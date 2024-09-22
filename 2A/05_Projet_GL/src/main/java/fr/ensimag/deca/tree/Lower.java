package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BGE;
import fr.ensimag.ima.pseudocode.instructions.BLT;
import fr.ensimag.ima.pseudocode.instructions.SLT;

/**
 *
 * @author gl10
 * @date 01/01/2024
 */
public class Lower extends AbstractOpIneq {

    public Lower(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public boolean isLT()
    {
        return true ;
    }

    @Override
    protected void codeCondition(boolean bool, Label etiquette, DecacCompiler compiler) {
        this.codeGenInst(compiler);
        if (bool)
            compiler.addInstruction(new BLT(etiquette));
        else
            compiler.addInstruction(new BGE(etiquette));
    }

    @Override
    protected void codeGenInit(DecacCompiler compiler) {
        compiler.addInstruction(new SLT(RegisterManager.getReg()));
    }

    @Override
    protected String getOperatorName() {
        return "<";
    }

}
