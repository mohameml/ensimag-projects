package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.OPP;
import fr.ensimag.ima.pseudocode.instructions.SUB;

/**
 * @author gl10
 * @date 01/01/2024
 */
public class Minus extends AbstractOpArith {
    public Minus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public boolean isMinus()
    {
        return true ;
    }

    @Override
    protected String getOperatorName() {
        return "-";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        boolean nocheck = compiler.getCompilerOptions().getNocheck();
        compiler.addInstruction( new SUB(regOp1, regOp2) );
        if(resultIsFloat()&& !nocheck) {
            compiler.addInstruction(new BOV(new Label("Debordement_arithmetique")));
        }
        compiler.addInstruction(new OPP(regOp2, Register.getR(RegisterManager.getNumR())));

    }

    @Override
    protected void codeCondition(boolean bool, Label etiquette, DecacCompiler compiler) {
        this.codeGenInst(compiler);
    }
}
