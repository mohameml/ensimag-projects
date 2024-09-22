package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;


/**
 * Left-hand side value of an assignment.
 * 
 * @author gl10
 * @date 01/01/2024
 */
public abstract class AbstractLValue extends AbstractExpr {
    protected abstract void codeGenAssign(DecacCompiler compiler);
}
