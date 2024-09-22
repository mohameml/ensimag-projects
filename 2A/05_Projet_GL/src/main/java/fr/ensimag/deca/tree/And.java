package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;

/**
 *
 * @author gl10
 * @date 01/01/2024
 */
public class And extends AbstractOpBool {
    private static int nEtiquette = 0;

    public And(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public boolean isAnd()
    {
        return true ;
    }

    @Override
    protected String getOperatorName() {
        return "&&";
    }


    protected void codeCondition(boolean bool, Label etiquette,DecacCompiler compiler){
        AbstractExpr C1 = getLeftOperand();
        AbstractExpr C2 = getRightOperand();
        if(bool){
            Label E_Fin = new Label("E_Fin." + nEtiquette);
            nEtiquette++;
            C1.codeCondition(false, E_Fin , compiler );
            C2.codeCondition(true , etiquette, compiler );
            compiler.addLabel(E_Fin);
        }else {
            C1.codeCondition(false ,etiquette, compiler);
            C2.codeCondition(false ,etiquette, compiler);
        }
    }
}



