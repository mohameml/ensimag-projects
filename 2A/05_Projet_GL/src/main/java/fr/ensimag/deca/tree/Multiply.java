package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.MUL;

/**
 * @author gl10
 * @date 01/01/2024
 */
public class Multiply extends AbstractOpArith {
    public Multiply(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public boolean isMult()
    {
        return true;
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        boolean nocheck = compiler.getCompilerOptions().getNocheck();
        super.codeGenInst(compiler);
        compiler.addInstruction( new MUL(regOp1, regOp2));
        if(resultIsFloat() && !nocheck) {
            compiler.addInstruction(new BOV(new Label("Debordement_arithmetique")));
        }
    }

    @Override
    protected void codeCondition(boolean bool, Label etiquette, DecacCompiler compiler) {
        this.codeGenInst(compiler);
    }

    @Override
    protected String getOperatorName() {
        return "*";
    }

}
