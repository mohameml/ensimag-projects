package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.*;


/**
 *
 * @author gl10
 * @date 01/01/2024
 */
public class Modulo extends AbstractOpArith {

    public Modulo(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public boolean isMod()
    {
        return true ;
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        boolean nocheck = compiler.getCompilerOptions().getNocheck();
        super.codeGenInst(compiler);
        compiler.addInstruction(new REM(regOp2, regOp1));
        compiler.addInstruction(new LOAD(regOp1, RegisterManager.getReg()));
        if(!nocheck){
            compiler.addInstruction(new BOV(new Label("Modulo_zero")));
        }
    }

    @Override
    protected String getOperatorName() {
        return "%";
    }

}
