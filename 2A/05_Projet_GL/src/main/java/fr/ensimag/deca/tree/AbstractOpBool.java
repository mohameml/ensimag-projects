package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.util.Util;

/**
 *
 * @author gl10
 * @date 01/01/2024
 */
public abstract class AbstractOpBool extends AbstractBinaryExpr {

    public AbstractOpBool(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {

        Type type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type typ2 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);

        Type type =  Util.type_binary_op(compiler, this, type1, typ2);

        if(type==null)
        {
            String left = this.getLeftOperand().getType().getName().getName();
            String right =  this.getRightOperand().getType().getName().getName(); 

            String description = String.format("l'opération %s n'est pas possible entre '%s' et '%s' ",this.getOperatorName() , left , right);

            // String description ="cette opération n'est pas possible:";
            throw new ContextualError(description, getLocation());
        }
        
        this.setType(type);
        return type ;

    }

}
