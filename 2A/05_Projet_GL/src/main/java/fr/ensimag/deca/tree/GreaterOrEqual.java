package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BGE;
import fr.ensimag.ima.pseudocode.instructions.BLT;
import fr.ensimag.ima.pseudocode.instructions.SGE;

/**
 * Operator "x >= y"
 * 
 * @author gl10
 * @date 01/01/2024
 */
public class GreaterOrEqual extends AbstractOpIneq {

    public GreaterOrEqual(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public boolean isGEQ()
    {
        return true ;
    }

    @Override
    protected String getOperatorName() {
        return ">=";
    }

    @Override
    protected void codeCondition(boolean bool, Label etiquette, DecacCompiler compiler) {
        this.codeGenInst(compiler);
        if(bool)
            compiler.addInstruction(new BGE(etiquette));
        else
            compiler.addInstruction(new BLT(etiquette));
    }

    @Override
    protected void codeGenInit(DecacCompiler compiler) {
        compiler.addInstruction(new SGE(RegisterManager.getReg()));
    }
}
