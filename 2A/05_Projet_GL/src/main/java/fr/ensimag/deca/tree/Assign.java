package fr.ensimag.deca.tree;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;

import fr.ensimag.ima.pseudocode.instructions.STORE;


/**
 * Assignment, i.e. lvalue = expr.
 *
 * @author gl10
 * @date 01/01/2024
 */
public class Assign extends AbstractBinaryExpr {

    @Override
    public AbstractLValue getLeftOperand() {
        // The cast succeeds by construction, as the leftOperand has been set
        // as an AbstractLValue by the constructor.
        return (AbstractLValue)super.getLeftOperand();
    }

    public Assign(AbstractLValue leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        
        Type t = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);   
        this.getRightOperand().verifyRValue(compiler, localEnv, currentClass, t);
        
        if(t.isFloat() && this.getRightOperand().getType().isInt())
        {
            ConvFloat convfloat = new ConvFloat(getRightOperand()) ;
            convfloat.setType(compiler.environmentType.FLOAT);
            this.setRightOperand(convfloat);
        }

        this.setType(t);
        return t ;
    }


    @Override
    protected String getOperatorName() {
        return " = ";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        getLeftOperand().codeGenAssign(compiler);
        //Identifier leftOperand = (Identifier) (getLeftOperand());
        //compiler.addInstruction(new STORE(regOp2, leftOperand.getExpDefinition().getOperand() ));
    }

}
