package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.tools.SymbolTable;

import fr.ensimag.ima.pseudocode.instructions.LOAD;

import static fr.ensimag.ima.pseudocode.Register.R0;


/**
 *
 * @author Ensimag
 * @date 01/01/2024
 */
public class IntType extends Type {

    public IntType(SymbolTable.Symbol name) {
        super(name);
    }

    @Override
    public boolean isInt() {
        return true;
    }

    @Override
    public boolean sameType(Type otherType) {
        return otherType.isInt();

    }

    @Override
    public void codeGenReturn(DecacCompiler compiler) {
        compiler.addInstruction(new LOAD(R0, RegisterManager.getReg()));
    }
}
