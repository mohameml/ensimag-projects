package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 *
 * @author gl10
 * @date 13/01/2024
 */

public class MethodBody extends AbstractMethodBody {

    private  ListDeclVar vars ;
    private  ListInst insts ;

    public MethodBody(ListDeclVar vars, ListInst insts) {
        Validate.notNull(vars);
        Validate.notNull(insts);
        this.vars = vars;
        this.insts = insts;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.println("{");
        s.indent();
        vars.decompile(s);
        insts.decompile(s);
        s.unindent();
        s.println("}");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        vars.iter(f);
        insts.iter(f);
    }
 
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        vars.prettyPrint(s, prefix, false);
        insts.prettyPrint(s, prefix, true);
    }

    @Override
    protected void verifyMethodBody(DecacCompiler compiler, EnvironmentExp LocalEnv, ClassDefinition classe,Type returnType) throws ContextualError {

        this.vars.verifyListDeclVariable(compiler, LocalEnv, classe);
        this.insts.verifyListInst(compiler, LocalEnv, classe, returnType);

    }
    @Override
    protected void codeGenMethod(DecacCompiler compiler) {
        vars.codeGenListDeclVarLocal(compiler);
        insts.codeGenListInst(compiler);
        
    }
}
