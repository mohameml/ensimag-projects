package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.Register;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

import static fr.ensimag.ima.pseudocode.Register.GB;

/**
 * @author gl10
 * @date 01/01/2024
 */
public class DeclVar extends AbstractDeclVar {

    
    final private AbstractIdentifier type;
    final private AbstractIdentifier varName;
    final private AbstractInitialization initialization;

    public static void setI(int i) {
        DeclVar.i = i;
    }

    public static int getI() {
        return i;
    }

    private static int i;

    private static int j=1;
    public DeclVar(AbstractIdentifier type, AbstractIdentifier varName, AbstractInitialization initialization) {
        Validate.notNull(type);
        Validate.notNull(varName);
        Validate.notNull(initialization);
        this.type = type;
        this.varName = varName;
        this.initialization = initialization;
    }
    
    
    

    @Override
    protected void verifyDeclVar(DecacCompiler compiler,EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {

        
        Type typeVar =  this.type.verifyType(compiler);


        if(typeVar.isVoid())
        {
            String description = "le type attendu doit étre différent de void";
            throw new ContextualError(description, getLocation());
        }

        Symbol symbol  =  this.varName.getName();
        
        initialization.verifyInitialization(compiler, typeVar, localEnv, currentClass);
        try {

            // System.out.println("Symbole = " + symbol + " de Type : " + typeVar + "a éte déclarée en " + localEnv);
            VariableDefinition var = new VariableDefinition(typeVar, getLocation()) ;
            this.type.setDefinition(compiler.environmentType.defOfType(typeVar.getName()));
            this.varName.setDefinition(var);
            localEnv.declare(symbol, var);
            // i++;
        } 

        catch (Exception e) {
            
            String description = String.format("%s est déjà déclarée", this.varName.getName().getName());
            throw new ContextualError(description, getLocation());
        }
    
    }

    
    @Override
    public void decompile(IndentPrintStream s) {
        type.decompile(s);
        s.print(" ");
        varName.decompile(s);
        initialization.decompile(s);
        s.print(";");
        

    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        type.iter(f);
        varName.iter(f);
        initialization.iter(f);
    }
    
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        varName.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, true);
    }

    @Override
    protected void codeGenDeclVar(DecacCompiler compiler) {
        DAddr operand = new RegisterOffset(i, GB);
        varName.getVariableDefinition().setOperand(operand);
        i++;
        this.initialization.codeGenInit(compiler, varName);
    }

    protected void codeGenDeclVarLocal(DecacCompiler compiler){ //var local à une methode
        DAddr operand = new RegisterOffset(j, Register.LB);
        varName.getVariableDefinition().setOperand(operand);
        j++;
        this.initialization.codeGenInit(compiler,varName);
        
    }

}
