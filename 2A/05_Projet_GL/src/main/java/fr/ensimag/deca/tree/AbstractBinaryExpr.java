package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

/**
 * Binary expressions.
 *
 * @author gl10
 * @date 01/01/2024
 */
public abstract class AbstractBinaryExpr extends AbstractExpr {
    protected GPRegister regOp1;
    protected GPRegister regOp2;
    public AbstractExpr getLeftOperand() {
        return leftOperand;
    }

    public AbstractExpr getRightOperand() {
        return rightOperand;
    }

    protected void setLeftOperand(AbstractExpr leftOperand) {
        Validate.notNull(leftOperand);
        this.leftOperand = leftOperand;
    }

    protected void setRightOperand(AbstractExpr rightOperand) {
        Validate.notNull(rightOperand);
        this.rightOperand = rightOperand;
    }

    private AbstractExpr leftOperand;
    private AbstractExpr rightOperand;

    public AbstractBinaryExpr(AbstractExpr leftOperand,
            AbstractExpr rightOperand) {
        Validate.notNull(leftOperand, "left operand cannot be null");
        Validate.notNull(rightOperand, "right operand cannot be null");
        Validate.isTrue(leftOperand != rightOperand, "Sharing subtrees is forbidden");
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }


    @Override
    public void decompile(IndentPrintStream s) {
        getLeftOperand().decompile(s);
        s.print(getOperatorName());
        getRightOperand().decompile(s);  
    }

    abstract protected String getOperatorName();

    @Override
    protected void iterChildren(TreeFunction f) {
        leftOperand.iter(f);
        rightOperand.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        leftOperand.prettyPrint(s, prefix, false);
        rightOperand.prettyPrint(s, prefix, true);
    }
    protected boolean resultIsFloat(){
        return this.resultIsFloat();
    }



    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        if( !(this instanceof Assign))
            leftOperand.codeGenInst(compiler);
        int registerOfOp1 = RegisterManager.getNumR();
        RegisterManager.setRegisterOfOp1(registerOfOp1);
        RegisterManager.incrementNum();
        boolean maxOver = RegisterManager.maxOver();
        if (!maxOver)
            regOp1 = Register.getR(RegisterManager.getRegisterOfOp1());

        rightOperand.codeGenInst(compiler);
        if (maxOver){
            regOp1 = Register.getR(0);
            regOp2 = Register.getR(RegisterManager.getNumR());
            RegisterManager.popFromStack(regOp1);
        }
        else{
            regOp2 = Register.getR(RegisterManager.getNumR());/*getNumR est implicitement le registre de l'op2*/
        }
    }
}
