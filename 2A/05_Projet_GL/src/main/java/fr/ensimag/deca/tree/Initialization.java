package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

/**
 * @author gl10
 * @date 01/01/2024
 */
public class Initialization extends AbstractInitialization {

    public AbstractExpr getExpression() {
        return expression;
    }

    private AbstractExpr expression;

    public void setExpression(AbstractExpr expression) {
        Validate.notNull(expression);
        this.expression = expression;
    }

    public Initialization(AbstractExpr expression) {
        Validate.notNull(expression);
        this.expression = expression;
    }

    @Override
    protected void verifyInitialization(DecacCompiler compiler, Type t,EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {

        
        AbstractExpr expr = this.expression.verifyRValue(compiler, localEnv, currentClass, t);
    
        if(t.isFloat() && expr.getType().isInt())
        {
            ConvFloat convlfot = new ConvFloat(getExpression());
            convlfot.setType(compiler.environmentType.FLOAT);
            this.setExpression(convlfot);
        }
        else 
        {

            this.setExpression(expr);
        }


    }


    @Override
    public void decompile(IndentPrintStream s) {
        //throw new UnsupportedOperationException("Not yet implemented");
        s.print(" = ");
        getExpression().decompile(s);
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        expression.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expression.prettyPrint(s, prefix, true);
    }

    @Override
    protected void codeGenInit(DecacCompiler compiler, AbstractIdentifier varName) {
        // On pourrai faire le store à la sortie -> plus besoin de varName en param
        getExpression().codeGenInst(compiler);
        DAddr operand = (varName).getExpDefinition().getOperand();
        if(expression instanceof AbstractOpCmp){
            ((AbstractOpCmp) expression).codeGenInit(compiler);
        }
        compiler.addInstruction(new STORE(RegisterManager.getReg(), operand));
    }

    @Override
    protected void codeGenInitField(DecacCompiler compiler){
        getExpression().codeGenInst(compiler);
        if(expression instanceof AbstractOpCmp){
            ((AbstractOpCmp) expression).codeGenInit(compiler);
        }
        RegisterManager.setRegisterOfOp1(RegisterManager.getNumR());
    }
}
