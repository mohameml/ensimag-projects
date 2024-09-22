package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.util.Util;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WFLOAT;
import fr.ensimag.ima.pseudocode.instructions.WFLOATX;
import fr.ensimag.ima.pseudocode.instructions.WINT;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

/**
 * Expression, i.e. anything that has a value.
 *
 * @author gl10
 * @date 01/01/2024
 */
public abstract class AbstractExpr extends AbstractInst {

    /**
     * @return true if the expression does not correspond to any concrete token
     * in the source code (and should be decompiled to the empty string).
     */
    boolean isImplicit() {
        return false;
    }

    /**
     * Get the type decoration associated to this expression (i.e. the type computed by contextual verification).
     */
    public Type getType() {
        return type;
    }

    protected void setType(Type type) {
        Validate.notNull(type);
        this.type = type;
    }
    
    private Type type;

    @Override
    protected void checkDecoration() {
        if (getType() == null) {
            throw new DecacInternalError("Expression " + decompile() + " has no Type decoration");
        }
    }

    /**
     * Verify the expression for contextual error.
     * 
     * implements non-terminals "expr" and "lvalue" 
     *    of [SyntaxeContextuelle] in pass 3
     *
     * @param compiler  (contains the "env_types" attribute)
     * @param localEnv
     *            Environment in which the expression should be checked
     *            (corresponds to the "env_exp" attribute)
     * @param currentClass
     *            Definition of the class containing the expression
     *            (corresponds to the "class" attribute)
     *             is null in the main bloc.
     * @return the Type of the expression
     *            (corresponds to the "type" attribute)
     */
    public abstract Type verifyExpr(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError;

    /**
     * Verify the expression in right hand-side of (implicit) assignments 
     * 
     * implements non-terminal "rvalue" of [SyntaxeContextuelle] in pass 3
     *
     * @param compiler  contains the "env_types" attribute
     * @param localEnv corresponds to the "env_exp" attribute
     * @param currentClass corresponds to the "class" attribute
     * @param expectedType corresponds to the "type1" attribute            
     * @return this with an additional ConvFloat if needed...
     */
    public AbstractExpr verifyRValue(DecacCompiler compiler,EnvironmentExp localEnv, ClassDefinition currentClass, Type expectedType) throws ContextualError {

        //  Régle (3.28) 
        
        Type t2 = this.verifyExpr(compiler, localEnv, currentClass);

        if(!Util.assignCompatibe(compiler, expectedType, t2))
        {   
            String description = String.format("le %s ne peut pas être affecté à %s ", t2.getName().getName() , expectedType.getName().getName());
            throw new ContextualError(description, getLocation());
        }
        //return AbstractExpr
        this.setType(t2);
        return this ;


    }
    
    
    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass, Type returnType) throws ContextualError {
        
        this.verifyExpr(compiler, localEnv, currentClass);

    }

    /**
     * Verify the expression as a condition, i.e. check that the type is
     * boolean.
     *
     * @param localEnv
     *            Environment in which the condition should be checked.
     * @param currentClass
     *            Definition of the class containing the expression, or null in
     *            the main program.
     */
    void verifyCondition(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        
        // Régle (3.29) :
        Type t = this.verifyExpr(compiler, localEnv, currentClass);
        
        if(!t.isBoolean())
        {
         
            String description = "le type attendu est boolean";
            
            throw new ContextualError(description, getLocation());
        }
    }

    /**
     * Generate code to print the expression
     *
     * @param compiler
     */
    //protected void codeGenPrint(DecacCompiler compiler) {
    //    this.codeGenInst(compiler);
    //    compiler.addInstruction(new LOAD(RegisterManager.getReg(), Register.R1));
    //}

    protected void codeGenPrintHex(DecacCompiler compiler,boolean printHex) {
        this.codeGenInst(compiler);
        compiler.addInstruction(new LOAD(RegisterManager.getReg(), Register.R1));
        if(resultIsFloat()){
            if (printHex){
                compiler.addInstruction(new WFLOATX());
            }else{
                compiler.addInstruction(new WFLOAT());
            }
        }else {
            compiler.addInstruction(new WINT());
        }
    }

    protected boolean resultIsFloat() {
        return false;
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        /* le codeGen de LValue fait appelle à ça et ça doit etre vide
         si un jour on doit modifier pour y mettre qlq chose alors,
         Il faut redifinir celui de Lvalue
         */
    }
    

    @Override
    protected void decompileInst(IndentPrintStream s) {
        decompile(s);
        s.print(";");
    }

    @Override
    protected void prettyPrintType(PrintStream s, String prefix) {
        Type t = getType();
        if (t != null) {
            s.print(prefix);
            s.print("type: ");
            s.print(t);
            s.println();
        }
    }


    /**
     * verifier si AbstractExpr est de type Minus  
     */
    public boolean isMinus()
    {
        return false ;
    }


    /**
     * verifier si AbstractExpr est de type Plus 
     */
    public boolean isPlus()
    {
        return false ;
    }


    /**
     * verifier si AbstractExpr est de type Divide 
     */
    public boolean isDivide()
    {
        return false ;
    }

    /**
     * verifier si AbstractExpr est de type Mult
     */
    public boolean isMult()
    {
        return false ;
    }

    /**
     * verifier si AbstractExpr est de type Mod
     */
    public boolean isMod()
    {
        return false ;
    }

    /**
     * verifier si AbstractExpr est de type EQ : Equals
     */
    public boolean isEQ()
    {
        return false ;
    }


    /**
     * verifier si AbstractExpr est de type NEQ : NotEquals
     */
    public boolean isNEQ()
    {
        return false ;
    }

    /**
     * verifier si AbstractExpr est de type LT :Lower
     */
    public boolean isLT()
    {
        return false ;
    }

    /**
     * verifier si AbstractExpr est de type GT : Greater
     */
    
    public boolean isGT()
    {
        return false ;
    }

    /**
     * verifier si AbstractExpr est de type LEQ : LowerOrEquals
     */
    public boolean isLEQ()
    {
        return false ;
    }

    /**
     * verifier si AbstractExpr est de type GEQ : GreaterOrEquals
     */
    public boolean isGEQ()
    {
        return false ;
    }

    /**
     * verifier si AbstractExpr est de type And 
     */

    public boolean isAnd()
    {
        return false ;
    }

    /**
     * verifier si AbstractExpr est de type OR  
     */
    public boolean isOR()
    {
        return false ;
    }

    /**
     * verifier si AbstractExpr est de type NOT   
     */
    public boolean isNOT()
    {
        return false ;
    }


    protected void codeCondition(boolean bool, Label etiquette,DecacCompiler compiler) {
    }

    public void CodeGenEmpile(DecacCompiler compiler) {
    }
}
