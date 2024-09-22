package fr.ensimag.deca.tree;



import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;



/**
 *
 * @author gl10
 * @date 13/01/2024
 */
public abstract class AbstractMethodBody extends Tree  {


    /**
     *  verification du MethodeBody pour la passe 3 
     * @param compiler
     * @param LocalEnv
     * @param classe
     * @param returnType
     * @throws ContextualError
     */
    protected abstract void verifyMethodBody(DecacCompiler compiler , EnvironmentExp LocalEnv , ClassDefinition classe , Type returnType ) throws ContextualError;

    protected abstract void codeGenMethod(DecacCompiler compiler);
}
