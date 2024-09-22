package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

/**
 * Print statement (print, println, ...).
 *
 * @author gl10
 * @date 01/01/2024
 */
public abstract class AbstractPrint extends AbstractInst {

    private boolean printHex;
    private ListExpr arguments = new ListExpr();
    
    abstract String getSuffix();

    public AbstractPrint(boolean printHex, ListExpr arguments) {
        Validate.notNull(arguments);
        this.arguments = arguments;
        this.printHex = printHex;
    }

    public ListExpr getArguments() {
        return arguments;
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,ClassDefinition currentClass, Type returnType) throws ContextualError {

        for(AbstractExpr expr : this.arguments.getList())
        {
            Type type = expr.verifyExpr(compiler, localEnv, currentClass);

            if(!type.isString() && !type.isFloat() && !type.isInt() )
            {
                String description = "le type attendu int , ﬂoat , string" ;
                
                throw new ContextualError(description, getLocation());
            }
        }

    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        for (AbstractExpr a : getArguments().getList()) {
            /*J'ai beau cherché, j'ai pas trouvé meilleure solution
             que de modifier le prototype. En effet il me fallait
             passer printHex en parametre*/
            a.codeGenPrintHex(compiler,printHex);
        }
    }
    private boolean getPrintHex() {
        return printHex;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        
        if(getPrintHex())
        {
            s.print("print" + getSuffix() + "x" + "(");
            arguments.decompile(s);
            s.print(")");
            
        }else{
            s.print("print" + getSuffix() + "(");
            arguments.decompile(s);
            s.print(")");
        }
        s.print(";");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        arguments.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        arguments.prettyPrint(s, prefix, true);
    }

}
