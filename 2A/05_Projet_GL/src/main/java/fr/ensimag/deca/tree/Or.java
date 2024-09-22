package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;

/**
 *
 * @author gl10
 * @date 01/01/2024
 */
public class Or extends AbstractOpBool {

    public Or(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public boolean isOR()
    {
        return true ;
    }

    @Override
    protected String getOperatorName() {
        return "||";
    }

    @Override
    protected void codeCondition(boolean bool, Label etiquette, DecacCompiler compiler) {
        AbstractExpr C1 = this.getLeftOperand();
        AbstractExpr C2 = this.getRightOperand();
        // 〈Code(C1 || C2, b, E)〉 ≡ 〈Code(!(!C1 && !C2)), b, E)〉
        ( new Not(new And(new Not(C1), new Not(C2))) ).codeCondition(bool, etiquette, compiler);
    }
}
