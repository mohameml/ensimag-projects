package fr.ensimag.deca.tree;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import  fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.FieldDefinition;
import  fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;



import fr.ensimag.deca.codegen.RegisterManager;


import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.GPRegister;

import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.STORE;



/**
 * 
 * @author gl10
 * @date 12/01/2024

 */

public class DeclField extends AbstractDeclField {

    private Visibility visibility ;
    private AbstractIdentifier type ;
    private AbstractIdentifier chmapeName ;
    private AbstractInitialization initialization ;



    public DeclField(Visibility visibility , AbstractIdentifier type  , AbstractIdentifier chmapeName ,  AbstractInitialization initialization  )
    {
        Validate.notNull(visibility);
        Validate.notNull(type);
        Validate.notNull(chmapeName);
        Validate.notNull(initialization);

        this.visibility = visibility ;
        this.type = type ; 
        this.chmapeName = chmapeName ;
        this.initialization = initialization ;

    }

    public Visibility getVisibility()
    {
        return visibility;
    }

    @Override
    protected void verifyDeclField(DecacCompiler compiler , ClassDefinition superclasse , ClassDefinition currentclasse) throws ContextualError
    {
        /*
         *
         *  pour la passe 2 : régele (2.5)
         *
        */

        Type typeChamp  = this.type.verifyType(compiler);

        if(typeChamp.isVoid())
        {
            String description = "le type attendu doit étre différent de void";
            throw new ContextualError(description, getLocation());
        }


        /*
         * si l’identificateur name est déjà défini dans
         *   l’environnement des expressions de la super-classe, alors ce doit être un identificateur de champ
        */
        // superclasse.getMembers().getMap().containsKey(this.chmapeName.getName()) : version 1 super = le super class le plus proche
        if(superclasse.getMembers().contains(this.chmapeName.getName()))
        {    // !superclasse.getMembers().getMap().get(this.chmapeName.getName()).isField()
            if(!superclasse.getMembers().get(this.chmapeName.getName()).isField())
            {
                String description = String.format("une rédefinition inacceptable  du %s en tant que champ ",this.chmapeName.getName().getName());
                throw new ContextualError(description, getLocation());                
            }
        }

        try {
            int index = currentclasse.getNumberOfFields() + 1;
            currentclasse.setNumberOfFields(currentclasse.getNumberOfFields()+1);
            FieldDefinition def = new FieldDefinition(typeChamp, getLocation(), visibility, currentclasse, index);
            
            /*----------- déclaration du def dans currentclasse : -------------- */
            currentclasse.getMembers().declare(this.chmapeName.getName(), def);
            
            /*----------- décoration ----------------- */
            this.type.setDefinition(compiler.environmentType.defOfType(typeChamp.getName()));
            this.chmapeName.setDefinition(def);





        } catch (DoubleDefException  e) {
            String description = String.format("%s est déjà déclarée", this.chmapeName.getName().getName());
            throw new ContextualError(description, getLocation());
        }
    }


    @Override
    protected void verifyFiledInitialization(DecacCompiler compiler , EnvironmentExp EnvExp , ClassDefinition Currentclass) throws ContextualError 
    {
        Type typeChamp = type.verifyType(compiler);
        this.initialization.verifyInitialization(compiler, typeChamp, EnvExp, Currentclass);
    }


    @Override
    public void decompile(IndentPrintStream s) {
        if(visibility == Visibility.PROTECTED)
        {
            s.print("protected");
        }
        s.print(' ');
        type.decompile(s);
        s.print(' ');
        chmapeName.decompile(s);
        initialization.decompile(s);
        s.print(";");

    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        chmapeName.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        //throw new UnsupportedOperationException("Not yet supported");
        type.iter(f);
        chmapeName.iter(f);
        initialization.iter(f);
        
    }





    protected void codeGenField(DecacCompiler compiler){
        DAddr operand_this = new RegisterOffset(-2, Register.LB);
        this.chmapeName.getFieldDefinition().setOperand(operand_this);
        this.initialization.codeGenInitField(compiler);

        RegisterManager.incrementNum();
        boolean maxOver = RegisterManager.maxOver();

        This current = new This(false);
        current.codeGenInst(compiler);
        RegisterOffset operand_tas = new RegisterOffset( chmapeName.getFieldDefinition().getIndex() , RegisterManager.getReg());

        GPRegister fieldValue;
        if (maxOver){
            fieldValue = Register.getR(0);
            RegisterManager.popFromStack(fieldValue);
        }else{
            fieldValue = Register.getR(RegisterManager.getRegisterOfOp1());
        }

        compiler.addInstruction(new STORE(fieldValue, operand_tas));

    }


}
