package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.tree.Location;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

/**
 * Definition of a method parameter.
 *
 * @author gl10
 * @date 01/01/2024
 */
public class ParamDefinition extends ExpDefinition {

    public ParamDefinition(Type type, Location location) {
        super(type, location);
    }

    @Override
    public String getNature() {
        return "parameter";
    }

    @Override
    public boolean isExpression() {
        return true;
    }

    @Override
    public void codeGenDefinition(DecacCompiler compiler) {
        DAddr op = this.getOperand();
        compiler.addInstruction(new LOAD(op, RegisterManager.getReg()));
    }

    @Override
    public boolean isParam() {
        return true;
    }

}
