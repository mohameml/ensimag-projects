package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.context.Type;

/**
 * 
 * @author gl10
 * @date 13/01/2024
 */

public class ListDeclParam extends TreeList<AbstractDeclParam> {


    @Override
    public void decompile(IndentPrintStream s) {
        int i = 0;
        int last = size() -1 ;
        for (AbstractDeclParam c : getList()) {
            if(c != null)
            {
                c.decompile(s);
                if( i < last )
                {
                    s.print(", ");
                }
            }
            
            i++;
            
        }
    }
    
    /**
     *  passe 2 
     * @param compiler
     * @return
     * @throws ContextualError
     */
    Signature verifyListDeclParam(DecacCompiler compiler) throws ContextualError
    {
        Signature sig = new Signature();

        for(AbstractDeclParam param : this.getList())
        {
            Type type = param.verifyDeclParam(compiler);
            sig.add(type);
        }

        return sig ;
    }
    
    /**
     * passe 3 
     */
    void verifyListParamBody(DecacCompiler compiler , EnvironmentExp EnvExp) throws ContextualError
    {
        for(AbstractDeclParam param : this.getList())
        {
            param.verifyParamBody(compiler, EnvExp);
        }   
    }

    protected void codeGenListParam(DecacCompiler compiler){
        for (AbstractDeclParam param: this.getList()) {
            param.codeGenParam(compiler);
        }
        DeclParam.setI(-3);
    }
}
