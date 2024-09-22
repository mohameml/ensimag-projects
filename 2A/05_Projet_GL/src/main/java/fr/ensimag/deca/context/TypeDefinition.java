package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.Location;

/**
 * Definition of a Deca type (builtin or class).
 *
 * @author gl10
 * @date 01/01/2024
 */
public class TypeDefinition extends Definition {

    public TypeDefinition(Type type, Location location) {
        super(type, location);
    }


    @Override
    public String getNature() {
        return "type";
    }

    @Override
    public boolean isExpression() {
        return false;
    }

    @Override
    public void codeGenDefinition(DecacCompiler compiler) {
        throw new RuntimeException("Not yet implemented");
    }
}
