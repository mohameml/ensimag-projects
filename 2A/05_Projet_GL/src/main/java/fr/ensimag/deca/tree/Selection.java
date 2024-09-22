package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.util.Util;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

public class Selection extends AbstractLValue {
    
    private AbstractExpr expr ;
    private AbstractIdentifier name ;
    

    public Selection( AbstractIdentifier name  , AbstractExpr expr )
    {
        Validate.notNull(expr);
        Validate.notNull(name);

        this.expr = expr ;
        this.name = name ;

    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {

        Type type = this.expr.verifyExpr(compiler, localEnv, currentClass);


        if(!type.isClass())
        {
            String description = "Le type attendu doit être un type de classe";

            throw new ContextualError(description, getLocation());

        }

        FieldDefinition defField = this.name.verifyField(((ClassType)type).getDefinition().getMembers());

        /*-------------- décoration ------------ */
        this.setType(defField.getType());

        if(defField.getVisibility() == Visibility.PUBLIC)
        {
            return defField.getType();
        }
        else if(defField.getVisibility() == Visibility.PROTECTED) 
        {
            if(currentClass == null) {

                String description = "On ne peut pas accéder à un champ protégé dans 'main'";
                throw new ContextualError(description, getLocation());
            }

            if(!Util.subType(compiler, currentClass.getType(), defField.getContainingClass().getType()) ) {
                String description =String.format("%s est protege", this.name.getName().getName());
                throw new ContextualError(description, getLocation());
            }

            if(!Util.subType(compiler, type, currentClass.getType()))
            {
                String description =String.format("le type de %s n'est pas un sous-type de %s.", type.getName().getName() ,currentClass.getType().getName().getName());
                
                throw new ContextualError(description, getLocation());
            }
        
            return defField.getType();
        }
        else 
        {
            String description = "visibility attendu : public ou protected ";
            throw new ContextualError(description, getLocation());
        }






    }

    @Override
    public void decompile(IndentPrintStream s) {
        expr.decompile(s);
        s.print('.');
        name.decompile(s);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expr.prettyPrint(s, prefix, false);
        name.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        expr.iter(f);
        name.iter(f);
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        ((Identifier)expr).getDefinition().codeGenDefinition(compiler);
        int index = name.getFieldDefinition().getIndex();
        compiler.addInstruction(new LOAD(new RegisterOffset(index, RegisterManager.getReg()) , RegisterManager.getReg()));
    }

    @Override
    protected void codeGenAssign(DecacCompiler compiler) {
        ((Identifier)expr).getDefinition().codeGenDefinition(compiler);
        int index = name.getFieldDefinition().getIndex();
        compiler.addInstruction(new STORE(RegisterManager.getReg(),new RegisterOffset(index, RegisterManager.getReg())));
    }
}
