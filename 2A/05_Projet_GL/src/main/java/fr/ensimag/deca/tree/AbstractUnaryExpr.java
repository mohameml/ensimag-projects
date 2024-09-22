package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

/**
 * Unary expression.
 *
 * @author gl10
 * @date 01/01/2024
 */
public abstract class AbstractUnaryExpr extends AbstractExpr {

    protected GPRegister regOp;


    public AbstractExpr getOperand() {
        return operand;
    }
    private AbstractExpr operand;
    public AbstractUnaryExpr(AbstractExpr operand) {
        Validate.notNull(operand);
        this.operand = operand;
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        operand.codeGenInst(compiler);
        int registerOfOp = RegisterManager.getNumR();
        RegisterManager.setRegisterOfOp1(registerOfOp);
        RegisterManager.incrementNum();
        regOp = Register.getR(RegisterManager.getRegisterOfOp1());
    }

    protected abstract String getOperatorName();
  
    @Override
    public void decompile(IndentPrintStream s) {
        s.print(getOperatorName());
        operand.decompile(s);

    }

    @Override
    protected void iterChildren(TreeFunction f) {
        operand.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        operand.prettyPrint(s, prefix, true);
    }

}
