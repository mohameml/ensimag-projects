package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.SNE;

/**
 *
 * @author gl10
 * @date 01/01/2024
 */
public class NotEquals extends AbstractOpExactCmp {

    public NotEquals(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public boolean isNEQ()
    {
        return true ;
    }

    @Override
    protected String getOperatorName() {
        return "!=";
    }

    protected void codeCondition(boolean bool, Label etiquette, DecacCompiler compiler) {
        this.codeGenInst(compiler);
        if (bool){
            compiler.addInstruction(new BNE(etiquette));
        }
        else{
            compiler.addInstruction(new BEQ(etiquette));
        }

    }

    @Override
    protected void codeGenInit(DecacCompiler compiler) {
        compiler.addInstruction(new SNE(RegisterManager.getReg()));
    }
}