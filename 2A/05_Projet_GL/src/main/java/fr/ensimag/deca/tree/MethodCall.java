package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.*;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

import static fr.ensimag.ima.pseudocode.Register.SP;

/**
 * @author gl10
 * @date 13/01/2024 
 */
public class MethodCall extends AbstractExpr {
    
    private AbstractExpr expr ; 
    private AbstractIdentifier name  ; 
    private ListExpr listexpr ; 

    public MethodCall(AbstractExpr expr , AbstractIdentifier name  , ListExpr listexpr )
    {
        Validate.notNull(expr);
        Validate.notNull(name);
        Validate.notNull(listexpr);

        this.name = name ;
        this.expr = expr ;
        this.listexpr = listexpr;

    }
    // nv
    public MethodCall(AbstractIdentifier name  , ListExpr listexpr )
    {
        
        Validate.notNull(name);
        Validate.notNull(listexpr);

        this.name = name ;
        this.listexpr = listexpr;

    }
    

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {

        // regle  3.41 et 3.71 ----> 3.74
        Type type = null ; 
        if(expr != null) {

            type = this.expr.verifyExpr(compiler, localEnv, currentClass);
        }
        else {
            type = currentClass.getType();
        }


        if(!type.isClass())
        {
            String description = "Le type attendu doit être un type de classe";

            throw new ContextualError(description, getLocation());

        }

        MethodDefinition defMethode = this.name.verifyMethode(((ClassType)type).getDefinition().getMembers());


        if(this.listexpr.getList().size()!=0 && defMethode.getSignature().size()==0)
        {
            String description = String.format("la methode %s ne prend pas de paramétres", this.name.getName().getName()); 
            throw new ContextualError(description, getLocation());
        }

        if(this.listexpr.getList().size()!=defMethode.getSignature().size())
        {
            String nomMethode = this.name.getName().getName();
            int nbParam = defMethode.getSignature().size();
            int nbDonne = this.listexpr.getList().size();
            String description = String.format("La méthode %s prend %s paramètres alors que vous avez fourni %s paramètres", nomMethode , nbParam , nbDonne);
            throw new ContextualError(description, getLocation());
        }

        Signature sig = defMethode.getSignature();
        int i = 0 ;

        for(AbstractExpr expr : this.listexpr.getList())
        {
            expr.verifyRValue(compiler, localEnv, currentClass, sig.paramNumber(i));
            i++ ;
        }

        /*------------------------- décoration ------------------------------ */

        if(expr !=null) {


            this.expr.setType(type);
        }
        this.name.setDefinition(defMethode);
        this.setType(defMethode.getType());

        return defMethode.getType();
    
    } 

    @Override
    public void decompile(IndentPrintStream s) {
        if(expr != null)
        {
            expr.decompile(s);
            s.print('.');
        }
        name.decompile(s);
        s.print("(");
        listexpr.decompile(s);
        s.print(")");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        if(expr != null)
        {
            expr.prettyPrint(s, prefix, false);
        }
        
        name.prettyPrint(s, prefix, false);
        listexpr.prettyPrint(s, prefix, true);
        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        if(expr != null)
        {
            expr.iter(f);
        }
        
        name.iter(f);
        listexpr.iter(f);
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        int lenParam = listexpr.size() + 1;
        int index  = name.getMethodDefinition().getIndex();
        //DAddr addThis = ((Identifier)expr).getVariableDefinition().getOperand();
        DAddr addThis = ((Identifier)expr).getExpDefinition().getOperand();
        compiler.addInstruction(new ADDSP(lenParam));
        compiler.addInstruction(new LOAD(addThis, RegisterManager.getReg()));
        compiler.addInstruction(new STORE(RegisterManager.getReg(), new RegisterOffset(0, SP)));
        listexpr.CodeGenEmpile(compiler);
        compiler.addInstruction(new LOAD(new RegisterOffset(0, SP), RegisterManager.getReg()));
        compiler.addInstruction(new CMP(new NullOperand(), RegisterManager.getReg()));
        compiler.addInstruction(new BEQ(new Label("dereferencement.null")));
        compiler.addInstruction(new LOAD(new RegisterOffset(0, RegisterManager.getReg()), RegisterManager.getReg()));
        compiler.addInstruction(new BSR(new RegisterOffset(index, RegisterManager.getReg())));
        compiler.addInstruction(new SUBSP(lenParam));

        this.getType().codeGenReturn(compiler);

    }
    protected void codeCondition(boolean bool, Label etiquette,DecacCompiler compiler) {
        this.codeGenInst(compiler);
    }
}
