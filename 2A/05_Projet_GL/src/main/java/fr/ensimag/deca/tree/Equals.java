package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.SEQ;

/**
 *
 * @author gl10
 * @date 01/01/2024
 */
public class Equals extends AbstractOpExactCmp {

    public Equals(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public boolean isEQ()
    {
        return true ;
    }


    @Override
    protected String getOperatorName() {
        return "==";
    }

    @Override
    protected void codeCondition(boolean bool, Label etiquette, DecacCompiler compiler) {
        this.codeGenInst(compiler);
        if (bool){
            compiler.addInstruction(new BEQ(etiquette));
        }
        else{
            compiler.addInstruction((new BNE(etiquette)));
        }

    }

    @Override
    protected void codeGenInit(DecacCompiler compiler) {
        compiler.addInstruction(new SEQ(RegisterManager.getReg()));
    }
}
