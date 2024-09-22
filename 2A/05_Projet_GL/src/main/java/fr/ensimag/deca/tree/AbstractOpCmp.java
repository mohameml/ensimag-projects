package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.util.Util;
import fr.ensimag.ima.pseudocode.instructions.CMP;

/**
 *
 * @author gl10
 * @date 01/01/2024
 */
public abstract class AbstractOpCmp extends AbstractBinaryExpr {

    public AbstractOpCmp(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,ClassDefinition currentClass) throws ContextualError {

        Type type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type type2 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);

        Type type =  Util.type_binary_op(compiler, this, type1, type2);

        if(type==null)
        {
            String left = this.getLeftOperand().getType().getName().getName();
            String right =  this.getRightOperand().getType().getName().getName(); 

            String description = String.format("l'opération %s n'est pas possible entre '%s' et '%s' ",this.getOperatorName() , left , right);

            // String description ="cette opération n'est pas possible:";
            throw new ContextualError(description, getLocation());
        }
        
        /*----------------------- Enrichissement avec le noeud ConvFloat -------------------- */

        if(this.getLeftOperand().getType().isInt() && this.getRightOperand().getType().isFloat())

        {
            ConvFloat convfloat = new ConvFloat(this.getLeftOperand()) ;
            convfloat.setType(compiler.environmentType.FLOAT);
            this.setLeftOperand(convfloat);


        }

        else if(this.getLeftOperand().getType().isFloat() && this.getRightOperand().getType().isInt())

        {
            ConvFloat convfloat = new ConvFloat(this.getRightOperand()) ;
            convfloat.setType(compiler.environmentType.FLOAT);
            this.setRightOperand(convfloat);


        }

        
        this.setType(type);
        return type ;

    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        compiler.addInstruction( new CMP(regOp2,regOp1));
    }

    protected abstract void codeGenInit(DecacCompiler compiler);
}
