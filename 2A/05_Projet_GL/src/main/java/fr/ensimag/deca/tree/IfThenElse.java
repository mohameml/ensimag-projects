package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

/**
 * Full if/else if/else statement.
 *
 * @author gl10
 * @date 01/01/2024
 */
public class IfThenElse extends AbstractInst {
    
    private final AbstractExpr condition; 
    private final ListInst thenBranch;
    private ListInst elseBranch;
    private static int labelNumber = 0;

    public IfThenElse(AbstractExpr condition, ListInst thenBranch, ListInst elseBranch) {
        Validate.notNull(condition);
        Validate.notNull(thenBranch);
        Validate.notNull(elseBranch);
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    public AbstractExpr getCondition()
    {
        return condition;
    }

    public ListInst getThenBranch()
    {
        return thenBranch;
    }

    public ListInst getElsenBranch()
    {
        return elseBranch;
    }
    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass, Type returnType) throws ContextualError {
        
        this.condition.verifyCondition(compiler, localEnv, currentClass);
        this.thenBranch.verifyListInst(compiler, localEnv, currentClass, returnType);
        this.elseBranch.verifyListInst(compiler, localEnv, currentClass, returnType);
        
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        Label labelElse = new Label("etiq_else." + labelNumber);
        Label labelFin = new Label("etiq_fin." + labelNumber);
        labelNumber++;
        condition.codeCondition( false, labelElse, compiler);
        thenBranch.codeGenListInst(compiler);
        compiler.addInstruction(new BRA(labelFin));
        compiler.addLabel(labelElse);
        elseBranch.codeGenListInst(compiler);
        //Pour pouvoir passer à la suite du programme après "Then"
        compiler.addLabel(labelFin);

    }

    @Override
    public void decompile(IndentPrintStream s) {
        //throw new UnsupportedOperationException("not yet implemented");
        s.print("if(");
        getCondition().decompile(s);
        s.println(") {");
        s.indent();
        getThenBranch().decompile(s);
        s.unindent();
        s.println("} else {");
        s.indent();
        getElsenBranch().decompile(s);
        s.unindent();
        s.println("}");


    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        condition.iter(f);
        thenBranch.iter(f);
        elseBranch.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        condition.prettyPrint(s, prefix, false);
        thenBranch.prettyPrint(s, prefix, false);
        elseBranch.prettyPrint(s, prefix, true);
    }
}
