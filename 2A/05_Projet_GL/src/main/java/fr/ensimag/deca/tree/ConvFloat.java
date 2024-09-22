package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.util.Util;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.FLOAT;

/**
 * Conversion of an int into a float. Used for implicit conversions.
 * 
 * @author gl10
 * @date 01/01/2024
 */
public class ConvFloat extends AbstractUnaryExpr {
    public ConvFloat(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        // FIXE : regle Cast (3.39)
        Type type = this.getType(); // ?? : type ↓env_types ↑type
        Type type2 = this.getOperand().verifyExpr(compiler, localEnv, currentClass);

        if(!Util.castCompatibel(compiler, type2, type))
        {

            String description = String.format("%s  ne peut pas être converti en %s", type2.getName().getName() , type.getName().getName());
            throw new ContextualError(description, getLocation());
        }

        
       
        this.setType(type);
        return type ;
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        int i = RegisterManager.getNumR();
        compiler.addInstruction(new FLOAT(regOp, Register.getR(i) ) );
    }


    @Override
    protected String getOperatorName() {
        return "/* conv float */";
    }

}
