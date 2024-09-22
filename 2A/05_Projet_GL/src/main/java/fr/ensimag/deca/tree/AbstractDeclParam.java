package fr.ensimag.deca.tree;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;

/**
 * Declaration de paramétre 
 * 
 * @author gl10
 * @date 12/01/2024
 */

public abstract class AbstractDeclParam extends Tree {

    /**
     * verification de la déclaration des paramétres des methodes dans la passe 2 
     * @param compiler
     * @return
     * @throws ContextualError
     */
    protected abstract Type verifyDeclParam(DecacCompiler compiler) throws ContextualError ;

    /**
     * verification d'Utilisation des parames dans la method pourr la passe 3
     */
    protected abstract void verifyParamBody(DecacCompiler compiler , EnvironmentExp EnvExp) throws ContextualError;


    protected abstract void codeGenParam(DecacCompiler compiler);
    
}
