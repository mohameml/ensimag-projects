package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

import static fr.ensimag.ima.pseudocode.Register.R0;

public class Return extends AbstractInst {
    

    private AbstractExpr expr ; 

    public AbstractExpr getExpression() {
        return expr;
    }


    public void setExpression(AbstractExpr expression) {
        Validate.notNull(expression);
        this.expr = expression;
    }

    public Return(AbstractExpr expr)
    {
        Validate.notNull(expr);
        this.expr = expr ;
    }

    @Override
    protected void verifyInst(DecacCompiler compiler,EnvironmentExp localEnv, ClassDefinition currentClass, Type returnType) throws ContextualError
    {
        if(!returnType.isVoid())
        {

            AbstractExpr expression  = this.expr.verifyRValue(compiler, localEnv, currentClass, returnType);
            this.setExpression(expression);
        }
        else 
        {
            String description = "le type attendu doit étre différent de void";
            throw new ContextualError(description, getLocation());
        }

    
    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print("return ");
        expr.decompile(s);
        s.print(";");
    }



    @Override
    protected  void codeGenInst(DecacCompiler compiler) {
        expr.codeGenInst(compiler);
        compiler.addInstruction(new LOAD(RegisterManager.getReg() , R0));
    }


    @Override
    protected
    void iterChildren(TreeFunction f) {
        expr.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expr.prettyPrint(s, prefix, true);
    }

    
}
