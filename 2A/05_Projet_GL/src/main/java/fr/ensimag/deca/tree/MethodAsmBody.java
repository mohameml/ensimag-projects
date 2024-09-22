package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 *
 * @author gl10
 * @date 13/01/2024
*/

public  class MethodAsmBody extends AbstractMethodBody {

    private AbstractStringLiteral value ;

    public AbstractStringLiteral getValue()
    {
        return this.value ;
    }

    public MethodAsmBody(AbstractStringLiteral str )
    {
        Validate.notNull(str);
        this.value = str;
    }
    

    @Override
    public void decompile(IndentPrintStream s) {
        
        s.print("asm(");
        value.decompile(s);
        s.print(");");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        value.iter(f) ;

    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        value.prettyPrint(s, prefix, true);

    }


    @Override
    protected void verifyMethodBody(DecacCompiler compiler, EnvironmentExp LocalEnv, ClassDefinition classe,Type returnType) throws ContextualError {
        Type type = this.value.verifyExpr(compiler, LocalEnv, classe);
        
        if(!type.isString()) {
            String description = "type attendu dans Asm : String ";
            throw new ContextualError(description, getLocation());
        }   

    }

    @Override
    protected void codeGenMethod(DecacCompiler compiler){

    }

    
}
