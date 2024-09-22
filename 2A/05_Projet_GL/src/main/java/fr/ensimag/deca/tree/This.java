package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import org.apache.commons.lang.Validate;


import java.io.PrintStream;

/**
 * @author gl10 
 * @date 13/01/2024
 */
public class This extends AbstractExpr {

    private boolean bool ;

    public boolean getBool()
    {
        return this.bool ;
    }

    public void setBool(boolean b )
    {
        Validate.notNull(b);
        this.bool = b ;
    }

    public This(boolean b)
    {
        this.bool = b ;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        if(currentClass==null)
        {
            String description = "'this' ne peut pas être appelé en dehors d'une classe";
            throw new ContextualError(description, getLocation());
        }
        /*--------------- décoration ------------- */
        this.setType(currentClass.getType());

        return currentClass.getType();
    }

    @Override
    public void decompile(IndentPrintStream s) {
        if(!bool){
            s.print("this");
        }
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'prettyPrintChildren'");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'iterChildren'");
    }


    @Override
    protected void codeGenInst(DecacCompiler compiler){
        compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB), RegisterManager.getReg()));
    }

    
}
