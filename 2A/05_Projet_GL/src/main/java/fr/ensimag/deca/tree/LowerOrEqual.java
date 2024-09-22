package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BGT;
import fr.ensimag.ima.pseudocode.instructions.BLE;
import fr.ensimag.ima.pseudocode.instructions.SLE;

/**
 *
 * @author gl10
 * @date 01/01/2024
 */
public class LowerOrEqual extends AbstractOpIneq {
    public LowerOrEqual(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    public boolean isLEQ()
    {
        return true ;
    }


    @Override
    protected String getOperatorName() {
        return "<=";
    }

    @Override
    protected void codeCondition(boolean bool, Label etiquette, DecacCompiler compiler) {
        this.codeGenInst(compiler);
        if (bool)
            compiler.addInstruction(new BLE(etiquette));
        else
            compiler.addInstruction(new BGT(etiquette));
    }

    @Override
    protected void codeGenInit(DecacCompiler compiler) {
        compiler.addInstruction(new SLE(RegisterManager.getReg()));
    }

}
