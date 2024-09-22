package fr.ensimag.deca.tree;

import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

import java.io.PrintStream;

/**
 *
 * @author gl10
 * @date 01/01/2024
 */
public class BooleanLiteral extends AbstractExpr {

    private boolean value;

    public BooleanLiteral(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        
        this.setType(compiler.environmentType.BOOLEAN);
        return compiler.environmentType.BOOLEAN;

    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print(Boolean.toString(value));
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    @Override
    String prettyPrintNode() {
        return "BooleanLiteral (" + value + ")";
    }

    protected void codeGenInst(DecacCompiler compiler) {
        compiler.addInstruction( new LOAD(this.getValueInt(), Register.getR(RegisterManager.getNumR())));
    }

    private int getValueInt(){
        return value ? 1 : 0;
    }

    @Override
    protected void codeCondition(boolean bool, Label etiquette, DecacCompiler compiler) {
        if (this.getValue() == true){
            if(bool)
                compiler.addInstruction(new BRA(etiquette)); //BRA E
        //  else  pas de code
        }
        else{
            ( new Not(new BooleanLiteral( true )) ).codeCondition(bool, etiquette, compiler);
        }
    }
}
