package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

/**
 * Deca Identifier
 *
 * @author gl10
 * @date 01/01/2024
 */
public class Identifier extends AbstractIdentifier {
    
    @Override
    protected void checkDecoration() {
        if (getDefinition() == null) {

            throw new DecacInternalError("Identifier " + this.getName() + " has no attached Definition");
        }
    }

    @Override
    public Definition getDefinition() {
        return definition;
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * ClassDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a class definition.
     */
    @Override
    public ClassDefinition getClassDefinition() {
        try {
            return (ClassDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a class identifier, you can't call getClassDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * MethodDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a method definition.
     */
    @Override
    public MethodDefinition getMethodDefinition() {
        try {
            return (MethodDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a method identifier, you can't call getMethodDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * FieldDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public FieldDefinition getFieldDefinition() {
        try {
            return (FieldDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a field identifier, you can't call getFieldDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * VariableDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.getVariableDefinition
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public VariableDefinition getVariableDefinition() {
        try {
            return (VariableDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a variable identifier, you can't call getVariableDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a ExpDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public ExpDefinition getExpDefinition() {
        try {
            return (ExpDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a Exp identifier, you can't call getExpDefinition on it");
        }
    }

    @Override
    public void setDefinition(Definition definition) {
        this.definition = definition;
    }

    @Override
    public Symbol getName() {
        return name;
    }

    private Symbol name;

    public Identifier(Symbol name) {
        Validate.notNull(name);
        this.name = name;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        // 
        ExpDefinition def = localEnv.get(name); 
        
        if(def==null)
        {   
            // System.out.println("le symbol " + name + "ne peut pas trouvéee dans l'env "+ localEnv);
            String descriptoin = String.format("%s identificateur non déclaré", this.name.getName());
            throw new ContextualError(descriptoin, getLocation());
        }

        if(!def.isField() && !def.isParam() && !def.isExpression())
        {
            // Tester dans la partie Complet 
            String description = "nature attendu : champ , paramètre , varibale";
            throw new ContextualError(description, getLocation());
        }

        Type type = def.getType();

        this.setDefinition(def);
        this.setType(type);

        return type ;
    }

    /**
     * Implements non-terminal "type" of [SyntaxeContextuelle] in the 3 passes
     * @param compiler contains "env_types" attribute
     */
    @Override
    public Type verifyType(DecacCompiler compiler) throws ContextualError {
        
        // régle (0.2)
        if(compiler.environmentType.getMap().containsKey(this.name))
        {
            return compiler.environmentType.defOfType(name).getType();
        }
        else 
        {
            String description = "identiﬁcateur de type non déclaré";
            throw new ContextualError(description, getLocation());
        }

        
    }

    public MethodDefinition verifyMethode(EnvironmentExp envExp) throws ContextualError
    {   
        ExpDefinition def = envExp.get(this.name);

        if(def==null)
        {
            String descriptoin = String.format("%s identificateur non déclaré", this.name.getName());
            throw new ContextualError(descriptoin, getLocation());
        }
        
        if(!def.isMethod())
        {
            String description = "nature attendu : Methode";
            throw new ContextualError(description, getLocation());
        }

        /*----------------- décoration -------------- */
        this.setDefinition(def);

        return (MethodDefinition)definition;
    }
    

    public  FieldDefinition verifyField(EnvironmentExp envExp) throws ContextualError {
        
        ExpDefinition def = envExp.get(this.name);

        if(def==null)
        {
            String descriptoin = String.format("%s identificateur non déclaré", this.name.getName());
            throw new ContextualError(descriptoin, getLocation());
        }        
    

        if(!def.isField())
        {
            String description = "nature attendu : Field";
            throw new ContextualError(description, getLocation());
        }

        /*----------------- décoration -------------- */
        this.setDefinition(def);

        return (FieldDefinition)definition;
    }

    
    private Definition definition;


    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(name.toString());
    }

    @Override
    String prettyPrintNode() {
        return "Identifier (" + getName() + ")";
    }

    @Override
    protected void prettyPrintType(PrintStream s, String prefix) {
        Definition d = getDefinition();
        if (d != null) {
            s.print(prefix);
            s.print("definition: ");
            s.print(d);
            s.println();
        }
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        this.definition.codeGenDefinition(compiler);
    }

    @Override
    protected void codeGenAssign(DecacCompiler compiler){
        compiler.addInstruction(new STORE(RegisterManager.getReg(), this.getExpDefinition().getOperand() ));
    }

    @Override
    protected boolean resultIsFloat() {
        return this.definition.getType().isFloat();
    }

    @Override
    protected void codeCondition(boolean bool, Label etiquette, DecacCompiler compiler){
        this.codeGenInst(compiler);
        compiler.addInstruction(new CMP(0, RegisterManager.getReg()));
        if (bool) {
            compiler.addInstruction(new BNE(etiquette));
        }
        else {
            compiler.addInstruction(new BEQ(etiquette));
        }
    }
}
