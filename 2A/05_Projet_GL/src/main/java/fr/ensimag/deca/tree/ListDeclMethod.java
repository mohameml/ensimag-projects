package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;


/**
 * 
 * @author gl10
 * @date 12/01/2024
 */

 public class ListDeclMethod extends TreeList<AbstractDeclMethod> {

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclMethod c : getList()) {
            c.decompile(s);
            s.println();
        }
    }
    
    /**

     *  passe 2

     * @param compiler
     * @param superClass
     * @return
     * @throws ContextualError
     */
    void verifyListDeclMethod(DecacCompiler compiler , ClassDefinition superClass , ClassDefinition currentclasse) throws ContextualError
    {


        for(AbstractDeclMethod method : this.getList())
        {
            method.verifyDeclMethod(compiler, superClass , currentclasse);

        }

    }

    /**

     * passe 3 
     */

    void verifyListDeclMethodBody(DecacCompiler compiler , EnvironmentExp EnvExp , ClassDefinition Currentclass) throws ContextualError
    {
        for(AbstractDeclMethod method : this.getList())
        {
            method.verifyDeclMethodBody(compiler, EnvExp, Currentclass);

        }

    }


    protected void codeGenListMethod(DecacCompiler compiler) {
        for (AbstractDeclMethod method: this.getList()) {
            method.codeGenMethod(compiler);
        }

    }




}
