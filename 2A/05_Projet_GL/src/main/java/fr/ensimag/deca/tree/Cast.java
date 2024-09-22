package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.util.Util;

/**
 * @author gl10
 * @date 13/01/2024 
 * 
 */

public class Cast extends AbstractExpr {
    
    private AbstractIdentifier type ;
    private AbstractExpr expr ;

    public Cast( AbstractIdentifier type  , AbstractExpr expr )
    {
        Validate.notNull(expr);
        Validate.notNull(type);

        this.expr = expr ;
        this.type = type;

    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {

        Type type1 = this.type.verifyType(compiler);
        Type type2 = this.expr.verifyExpr(compiler, localEnv, currentClass);
        

        if(type2.isVoid()) {
            String description = "l'opération de casting n'est pas possible avce void";
            throw new ContextualError(description, getLocation());
        }

        if(!Util.castCompatibel(compiler, type2, type1))
        {
            String description = String.format("casting entre '%s' et '%s' n'est pas possible ", type1.getName().getName(), type2.getName().getName());
            throw new ContextualError(description, getLocation());
        }

        /*----------- décoration ------------- */
        this.type.setDefinition(compiler.environmentType.defOfType(type1.getName()));
        this.expr.setType(type2);
        this.setType(type1);

        return type1 ;

    }
    
    @Override
    public void decompile(IndentPrintStream s) {
        s.print('(');
        type.decompile(s);
        s.print(')');
        s.print('(');
        expr.decompile(s);
        s.print(')');
        
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        expr.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        expr.iter(f);
    }


}
