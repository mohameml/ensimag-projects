package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.instructions.TSTO;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import java.io.PrintStream;

/**
 * @author gl10
 * @date 01/01/2024
 */
public class Main extends AbstractMain {
    private static final Logger LOG = Logger.getLogger(Main.class);
    
    private ListDeclVar declVariables;
    private ListInst insts;

    public int lenVarGlob(){
        return declVariables.size();
    }
    public Main(ListDeclVar declVariables, ListInst insts) {
        Validate.notNull(declVariables);
        Validate.notNull(insts);
        this.declVariables = declVariables;
        this.insts = insts;
    }

    @Override
    protected void verifyMain(DecacCompiler compiler) throws ContextualError {
        // LOG.debug("verify Main: start");
        EnvironmentExp EnvExp = new EnvironmentExp(null);  
        Type returntype = compiler.environmentType.VOID;

        this.declVariables.verifyListDeclVariable(compiler, EnvExp, null);
        this.insts.verifyListInst(compiler, EnvExp, null, returntype);   
        
        // LOG.debug("verify Main: end");


    }
    @Override
    protected void codeGenMain(DecacCompiler compiler) {
        int nbTemp = RegisterManager.getNombreMaxTemp();
        /*varGlob +Tables des methodes (poly P211) */
        compiler.addFirst(new TSTO( nbTemp ));/*Taille max de la pile poly page 211*/
        compiler.addComment("Main program");
        compiler.addComment("Beginning of main instructions:");
        declVariables.codeGenListDeclVar(compiler);
        insts.codeGenListInst(compiler);
        
    }

    
    @Override
    public void decompile(IndentPrintStream s) {
        s.println("{");
        s.indent();
        declVariables.decompile(s);
        insts.decompile(s);
        s.unindent();
        s.println("}");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        declVariables.iter(f);
        insts.iter(f);
    }
 
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        declVariables.prettyPrint(s, prefix, false);
        insts.prettyPrint(s, prefix, true);
    }
}
