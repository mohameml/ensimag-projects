package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.tree.Location;
import fr.ensimag.deca.tree.Visibility;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

/**
 * Definition of a field (data member of a class).
 *
 * @author gl10
 * @date 01/01/2024
 */
public class FieldDefinition extends ExpDefinition {
    public int getIndex() {
        return index;
    }

    private int index;
    
    @Override
    public boolean isField() {
        return true;
    }

    private final Visibility visibility;
    private final ClassDefinition containingClass;
    
    public FieldDefinition(Type type, Location location, Visibility visibility,
            ClassDefinition memberOf, int index) {
        super(type, location);
        this.visibility = visibility;
        this.containingClass = memberOf;
        this.index = index;
    }
    
    @Override
    public FieldDefinition asFieldDefinition(String errorMessage, Location l)
            throws ContextualError {
        return this;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public ClassDefinition getContainingClass() {
        return containingClass;
    }

    @Override
    public String getNature() {
        return "field";
    }

    @Override
    public boolean isExpression() {
        return true;
    }



    @Override
    public void codeGenDefinition(DecacCompiler compiler) {
        DAddr operand_this = this.getOperand();
        compiler.addInstruction(new LOAD(operand_this , RegisterManager.getReg()));
        DAddr deref = new RegisterOffset(index, RegisterManager.getReg());
        compiler.addInstruction(new LOAD(deref, RegisterManager.getReg()));
    }

}
