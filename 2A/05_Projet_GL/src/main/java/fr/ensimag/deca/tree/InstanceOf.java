package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.util.Util;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

import static fr.ensimag.ima.pseudocode.Register.R0;

/**
 * @author gl10 
 * @date 13/01/2024 
 */
public class InstanceOf extends AbstractExpr {

    private AbstractExpr expr;
    private AbstractIdentifier name;


    public InstanceOf(AbstractExpr expr, AbstractIdentifier name) {
        Validate.notNull(expr);
        Validate.notNull(name);

        this.name = name;
        this.expr = expr;
    }


    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {

        Type type1 = this.expr.verifyExpr(compiler, localEnv, currentClass);
        Type type2 = this.name.verifyType(compiler);

        Type type = Util.type_instanceof_op(compiler, type1, type2);

        if (type == null) {
            String description = String.format("l'opération instanceOf n'est pas possible entre '%s' et '%s' ", type1.getName().getName(), type2.getName().getName());


            throw new ContextualError(description, getLocation());
        }

        /*------------ décoration -------------- */
        this.expr.setType(type1);
        this.name.setDefinition(compiler.environmentType.defOfType(type2.getName()));
        this.setType(type);

        return type;


    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print("(");
        expr.decompile(s);
        s.print(" instanceof ");
        name.decompile(s);
        s.print(")");

    }


    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expr.prettyPrint(s, prefix, false);
        name.prettyPrint(s, prefix, true);
    }


    @Override
    protected void iterChildren(TreeFunction f) {
        expr.iter(f);
        name.iter(f);
    }

    @Override
    protected void codeCondition(boolean bool, Label etiquette, DecacCompiler compiler) {
       DAddr adB = this.name.getClassDefinition().getTableMethodeclass();
       DAddr adC = ((ClassType)(expr.getType())).getDefinition().getTableMethodeclass();


       compiler.addInstruction(new LOAD(adC, R0));
       compiler.addInstruction(new CMP(adB, R0));
       compiler.addInstruction(new BEQ(etiquette));
       compiler.addInstruction(new LOAD(new RegisterOffset(0, R0),R0));
       compiler.addInstruction(new CMP(new NullOperand(), R0));
       compiler.addInstruction(new BOV(new Label("dereferencement.null")));

    }

}