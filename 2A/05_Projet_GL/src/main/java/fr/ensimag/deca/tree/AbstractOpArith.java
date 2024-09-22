package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.util.Util;

/**
 * Arithmetic binary operations (+, -, /, ...)
 * 
 * @author gl10
 * @date 01/01/2024
 */
public abstract class AbstractOpArith extends AbstractBinaryExpr {

    public AbstractOpArith(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
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
        
        this.setType(type);

        /*----------------------- Enrichissement avec le noeud ConvFloat -------------------- */
        if(type1.isInt() && type2.isFloat())
        {
            ConvFloat convfloat = new ConvFloat(this.getLeftOperand()) ;
            convfloat.setType(compiler.environmentType.FLOAT);
            this.setLeftOperand(convfloat);


        }
        else if(type1.isFloat() && type2.isInt())
        {
            ConvFloat convfloat = new ConvFloat(this.getRightOperand()) ;
            convfloat.setType(compiler.environmentType.FLOAT);
            this.setRightOperand(convfloat);


        }

        return type ;
    }

    @Override
    protected boolean resultIsFloat(){
        return this.getLeftOperand().getType().isFloat() ||
               this.getRightOperand().getType().isFloat();
    }
}
