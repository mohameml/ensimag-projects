package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.util.List;

/**
 * List of declarations (e.g. int x; float y,z).
 * 
 * @author gl10
 * @date 01/01/2024
 */
public class ListDeclVar extends TreeList<AbstractDeclVar> {

    @Override
    public void decompile(IndentPrintStream s) {
        //throw new UnsupportedOperationException("Not yet implemented");
        for (AbstractDeclVar c : getList()) {
            c.decompile(s);
            s.println();
        }

    }

    /**
     * Implements non-terminal "list_decl_var" of [SyntaxeContextuelle] in pass 3
     * @param compiler contains the "env_types" attribute
     * @param localEnv 
     *   its "parentEnvironment" corresponds to "env_exp_sup" attribute
     *   in precondition, its "current" dictionary corresponds to 
     *      the "env_exp" attribute
     *   in postcondition, its "current" dictionary corresponds to 
     *      the "env_exp_r" attribute
     * @param currentClass 
     *          corresponds to "class" attribute (null in the main bloc).
     */    
    void verifyListDeclVariable(DecacCompiler compiler, EnvironmentExp localEnv,ClassDefinition currentClass) throws ContextualError {

        List<AbstractDeclVar> listVar = this.getList();
        // EnvironmentExp envExpr = new EnvironmentExp(localEnv);
        for(AbstractDeclVar var : listVar)
        {
            var.verifyDeclVar(compiler, localEnv, currentClass);
        }

    
    }


    protected void codeGenListDeclVar(DecacCompiler compiler) { //Var global (GB)
        for (AbstractDeclVar var: this.getList()) {
            var.codeGenDeclVar(compiler);
        }
    }

    protected void codeGenListDeclVarLocal(DecacCompiler compiler){ //Var local à une méthode (LB)
        for (AbstractDeclVar var: this.getList()) {
            var.codeGenDeclVarLocal(compiler); 
        }
    }
}
