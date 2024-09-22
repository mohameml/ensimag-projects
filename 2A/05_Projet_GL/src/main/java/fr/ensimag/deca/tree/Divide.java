package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.DIV;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.QUO;

/**
 *
 * @author gl10
 * @date 01/01/2024
 */
public class Divide extends AbstractOpArith {
    public Divide(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public boolean isDivide()
    {
        return true ;
    }

    @Override
    protected String getOperatorName() {
        return "/";
    }


    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        boolean nocheck = compiler.getCompilerOptions().getNocheck();
        if (resultIsFloat()) {
            compiler.addInstruction(new DIV(regOp2, regOp1));
        }
        else {
            compiler.addInstruction(new QUO(regOp2, regOp1));
        }
        compiler.addInstruction(new LOAD(regOp1, RegisterManager.getReg()));
        if(resultIsFloat() && !nocheck) {
            compiler.addInstruction(new BOV(new Label("Debordement_arithmetique")));
        }
        if (!nocheck) {
            compiler.addInstruction(new BOV(new Label("Division_par_zero")));
        }

    }

}
