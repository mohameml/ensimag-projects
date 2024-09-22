package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.Location;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import org.apache.commons.lang.Validate;

import static fr.ensimag.ima.pseudocode.Register.GB;
import static fr.ensimag.ima.pseudocode.Register.R0;

/**
 * Definition of a method
 *
 * @author gl10
 * @date 01/01/2024
 */
public class MethodDefinition extends ExpDefinition {

    @Override
    public boolean isMethod() {
        return true;
    }

    public Label getLabel() {
        Validate.isTrue(label != null,
                "setLabel() should have been called before");
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public int getIndex() {
        return index;
    }

    private int index;

    @Override
    public MethodDefinition asMethodDefinition(String errorMessage, Location l)
            throws ContextualError {
        return this;
    }

    private final Signature signature;
    private Label label;
    
    /**
     * 
     * @param type Return type of the method
     * @param location Location of the declaration of the method
     * @param signature List of arguments of the method
     * @param index Index of the method in the class. Starts from 0.
     */
    public MethodDefinition(Type type, Location location, Signature signature, int index) {
        super(type, location);
        this.signature = signature;
        this.index = index;
    }

    public Signature getSignature() {
        return signature;
    }

    @Override
    public String getNature() {
        return "method";
    }

    @Override
    public boolean isExpression() {
        return false;
    }


    public static int getOffset() {
        return offset;
    }
    public static void offsetIncr() {
        offset++;
    }

    private static int offset = 3;

    public void empileMethode(DecacCompiler compiler) {
        if(this.label == null) return;
        DAddr opMethod = new RegisterOffset(offset, GB);
        compiler.addInstruction(new LOAD(new LabelOperand(label) , R0));
        compiler.addInstruction(new STORE(R0, opMethod));
        offset++;
    }

    @Override
    public void codeGenDefinition(DecacCompiler compiler) {
        /*Là il faut jump dans la methode puis revenir dans l'eval
        * de l'expr sachant que le resultat sera stocké dans R0*/
    }

}
