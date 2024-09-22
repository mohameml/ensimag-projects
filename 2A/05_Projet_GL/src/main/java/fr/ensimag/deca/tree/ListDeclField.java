package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * 
 * @author gl10
 * @date 12/01/2024
 */

public class ListDeclField extends TreeList<AbstractDeclField> {


    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclField c : getList()) {
            c.decompile(s);
            s.println();
        }
    }

    /**
     *  passe 2 
     * @param compiler
     * @param superclasse
     * @param currentclasse
     * @return
     * @throws ContextualError
     */
    void verifyListDeclField(DecacCompiler compiler , ClassDefinition superclasse , ClassDefinition currentclasse ) throws ContextualError 
    {

        for(AbstractDeclField field : this.getList())
        {
            field.verifyDeclField(compiler, superclasse, currentclasse );

        }

    }

    /**
     * passe 3 
     */
    void verifyListFieldInitialization(DecacCompiler compiler , EnvironmentExp EnvExp , ClassDefinition Currentclass) throws ContextualError
    {

        for(AbstractDeclField field : this.getList())
        {
            field.verifyFiledInitialization(compiler, EnvExp, Currentclass);

        }
    }


    protected void codeGenListField(DecacCompiler compiler){
        for (AbstractDeclField field: this.getList()) {
            field.codeGenField(compiler);
            RegisterManager.setNumR(2);
        }
    }


}
