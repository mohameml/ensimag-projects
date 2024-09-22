package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

import java.io.PrintStream;

/**
 * @author gl10 
 * @date 13/01/2024 
 */
public class Null extends AbstractExpr {

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Symbol NullSymb = compiler.createSymbol("null");
        NullType nullType = new NullType(NullSymb);
        this.setType(nullType);
        return nullType;

    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("null");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // Nothing to do
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // nothing to do

    }
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        compiler.addInstruction( new LOAD(new NullOperand(), RegisterManager.getReg()));
    }

}