package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.tree.Location;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

/**
 * Definition of a variable.
 *
 * @author gl10
 * @date 01/01/2024
 */
public class VariableDefinition extends ExpDefinition {
    public VariableDefinition(Type type, Location location) {
        super(type, location);
    }

    @Override
    public String getNature() {
        return "variable";
    }

    @Override
    public boolean isExpression() {
        return true;
    }

    @Override
    public void codeGenDefinition(DecacCompiler compiler) {
        DAddr op = this.getOperand();
        compiler.addInstruction(new LOAD(op , Register.getR(RegisterManager.getNumR())));
    }
}
