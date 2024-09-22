package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.util.Util;
import fr.ensimag.ima.pseudocode.Label;

/**
 *
 * @author gl10
 * @date 01/01/2024
 */
public class Not extends AbstractUnaryExpr {

    public Not(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public boolean isNOT()
    {
        return true ;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        
        // Régle (3.37) : op_un ↑op [ expr ↓env_types ↓env_exp ↓class ↑type1 ]  affectation type := type_unary_op(op, type1)
        // op_un ↑not → Not (3.63)

        Type type1 = this.getOperand().verifyExpr(compiler, localEnv, currentClass);
        Type type =  Util.type_unaru_op(compiler, this, type1);

        if(type==null)
        {
            String description = String.format("l'opération Not ! devant %s n'est pas valide", this.getOperand().getType().getName().getName());
            throw new ContextualError(description, getLocation());
        }

        this.setType(type);

        return type ;
    }

    protected void codeCondition(boolean bool, Label etiquette,DecacCompiler compiler){
        if (bool) {
            this.getOperand().codeCondition(false, etiquette, compiler);
        }
        else{
            this.getOperand().codeCondition(true, etiquette, compiler);
        }
    }



        @Override
    protected String getOperatorName() {
        return "!";
    }
}
