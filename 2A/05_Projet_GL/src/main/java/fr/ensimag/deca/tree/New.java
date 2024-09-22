package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.*;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

import static fr.ensimag.ima.pseudocode.Register.R0;

/**
 * @author gl10 
 * @date 13/01/2024 
 * 
 */
public class New  extends AbstractExpr {
   
    private AbstractIdentifier name ;

    public AbstractIdentifier getName()
    {
        return this.name ;
    }

    public New(AbstractIdentifier id)
    {
        Validate.notNull(id);   
        this.name = id ;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Type type = this.name.verifyType(compiler);

        if(!type.isClass())
        {
            String description = "Le type attendu doit être un type de classe";
            throw new ContextualError(description, getLocation());
        }

        /*--------------- décoration ------------------- */
        this.setType(type);
        this.name.setDefinition(((ClassType)type).getDefinition());
        return type ;
   
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("new ");
        name.decompile(s);
        s.print("()");

    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        boolean nocheck = compiler.getCompilerOptions().getNocheck();
        int tailleClass = name.getClassDefinition().getNumberOfFields() + 1;
        GPRegister addTas = RegisterManager.getReg();
        DAddr addTableMetode = name.getClassDefinition().getTableMethodeclass();
        //Label init = name.getClassDefinition().getInitEtiquette();
        Label init = new Label("init."+name.getName());
        compiler.addInstruction(new NEW(tailleClass, addTas));
        if(!nocheck){
            compiler.addInstruction(new BOV(new Label("tas_plein")));
        }
        compiler.addInstruction(new LEA(addTableMetode, R0));
        compiler.addInstruction(new STORE(R0, new RegisterOffset(0, addTas)));
        compiler.addInstruction(new PUSH(addTas));
        compiler.addInstruction(new BSR(init));
        compiler.addInstruction(new POP(addTas));
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        name.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        name.iter(f);
    }

    
}
