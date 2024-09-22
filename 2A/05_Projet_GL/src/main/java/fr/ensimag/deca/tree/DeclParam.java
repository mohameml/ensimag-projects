package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.context.ParamDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

public class DeclParam extends AbstractDeclParam {

    private AbstractIdentifier type ;
    private  AbstractIdentifier name  ;

    public DeclParam(AbstractIdentifier type , AbstractIdentifier name )
    {
        Validate.notNull(type);
        Validate.notNull(name);

        this.name = name ;
        this.type = type ;
    }

    public static void setI(int i) {
        DeclParam.i = i;
    }

    public static int getI() {
        return i;
    }

    private static int i=-3;

    @Override
    protected Type verifyDeclParam(DecacCompiler compiler) throws ContextualError {

        Type typeParam = this.type.verifyType(compiler);

        if(typeParam.isVoid())
        {
            String description = "le type attendu doit étre différent de void";
            throw new ContextualError(description, getLocation());
        }

        /*------------- décoration de def ------------- */
        this.type.setDefinition(compiler.environmentType.defOfType(typeParam.getName()));

        return typeParam;
    }


    @Override
    protected void verifyParamBody(DecacCompiler compiler , EnvironmentExp EnvExp) throws ContextualError
    {
        Type typeParam = this.type.verifyType(compiler);

        try {
            ParamDefinition def = new ParamDefinition(typeParam, getLocation());

            EnvExp.declare(this.name.getName(), def);

            /*---------- décoration du nom  ------------*/
            this.name.setDefinition(def);


        } catch (DoubleDefException e) {
            String description = String.format("%s est déjà déclarée", this.name.getName().getName());
            throw new ContextualError(description, getLocation());
        }
    }
    
    @Override
    public void decompile(IndentPrintStream s) {
        type.decompile(s);
        s.print(' ');
        name.decompile(s);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        name.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        name.iter(f);
    }

    @Override
    protected void codeGenParam(DecacCompiler compiler){
        DAddr operand = new RegisterOffset(i , Register.LB);
        ParamDefinition def = (ParamDefinition)name.getDefinition();
        def.setOperand(operand);
        i--;

    }
}
