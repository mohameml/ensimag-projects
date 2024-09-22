package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;


/**
 * Declaration of a methodes (<code>type nom (params )<code>).
 * 
 * @author gl10
 * @date 12/01/2024
 */

public abstract class AbstractDeclMethod extends Tree {
    

    /**
     * verification du déclaration des méthodes  pour la passe 2
     * @param compiler
     * @param superclasse
     * @param currentclasse
     * @param localEnv
     */
    protected abstract void verifyDeclMethod(DecacCompiler compiler , ClassDefinition superclasse ,
        ClassDefinition currentclasse ) throws ContextualError;

    /**
     * verification de la contenu des méthodes -----> passe 3 ;
     */
    protected  abstract void verifyDeclMethodBody(DecacCompiler compiler , EnvironmentExp EnvExp , ClassDefinition Currentclass ) throws ContextualError;




    protected abstract void codeGenMethod(DecacCompiler compiler);


    protected abstract void empileMethode(DecacCompiler compiler);

}
