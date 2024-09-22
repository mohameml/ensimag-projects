package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.ADD;
import fr.ensimag.ima.pseudocode.instructions.BOV;

/**
 * @author gl10
 * @date 01/01/2024
 */
public class Plus extends AbstractOpArith {
    public Plus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }
 
    @Override
    public boolean isPlus() {
        return true;
    }

    @Override
    protected String getOperatorName() {
        return "+";
    }


    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        boolean nocheck = compiler.getCompilerOptions().getNocheck();
        super.codeGenInst(compiler);
        compiler.addInstruction( new ADD(regOp1, regOp2));
        if(resultIsFloat() && !nocheck){
            compiler.addInstruction(new BOV(new Label("Debordement_arithmetique")));
        }
    }

    @Override
    protected void codeCondition(boolean bool, Label etiquette, DecacCompiler compiler) {
        this.codeGenInst(compiler);
    }
}
