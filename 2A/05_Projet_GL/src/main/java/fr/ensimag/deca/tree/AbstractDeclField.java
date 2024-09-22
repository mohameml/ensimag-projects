package fr.ensimag.deca.tree;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;


import java.io.PrintStream;

/**
 * Declaration of a fields (<code>visibility type name  init <code>).
 * 
 * @author gl10
 * @date 12/01/2024
 */

public abstract class AbstractDeclField extends Tree  {
    
    /**
     * verification du déclaration des champs pour la passe 2
     * @param compiler
     * @param superclasse
     * @param currentclasse
     * @param localEnv
     */
    protected abstract void verifyDeclField(DecacCompiler compiler , ClassDefinition superclasse ,
        ClassDefinition currentclasse ) throws ContextualError;

    /**
     * verification d'inisialisation des champs  ----> passe 3
     */
    protected abstract void verifyFiledInitialization(DecacCompiler compiler , EnvironmentExp EnvExp , ClassDefinition Currentclass) throws ContextualError ;




    protected abstract void codeGenField(DecacCompiler compiler);

}
