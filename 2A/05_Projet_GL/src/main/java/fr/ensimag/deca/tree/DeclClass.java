package fr.ensimag.deca.tree;



import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.DecacCompiler;


import fr.ensimag.deca.context.ContextualError;

import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.TypeDefinition;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.context.*;

import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;




import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;



import fr.ensimag.deca.context.MethodDefinition;







import static fr.ensimag.ima.pseudocode.Register.*;

import static fr.ensimag.ima.pseudocode.Register.GB;
import static fr.ensimag.ima.pseudocode.Register.R0;


/**
 * Declaration of a class (<code>class name extends superClass {members}<code>).
 * 
 * @author gl10
 * @date 01/01/2024
 */
public class DeclClass extends AbstractDeclClass {


    private AbstractIdentifier nom ;
    private AbstractIdentifier superClasse ;
    private ListDeclField champs ;
    private ListDeclMethod methodes ;



    public AbstractIdentifier getNom() {
        return this.nom;
    }

    public static int getOffsetSuper() {
        return offsetSuper;
    }

    private static int offsetSuper = 1;

    public DeclClass(AbstractIdentifier nom ,  AbstractIdentifier superClasse , ListDeclField champs  , ListDeclMethod methodes )
    {
        Validate.notNull(nom);
        Validate.notNull(superClasse);
        Validate.notNull(champs);
        Validate.notNull(methodes);

        this.nom = nom ;
        this.superClasse = superClasse ;
        this.champs = champs;
        this.methodes = methodes ;
    }

    public ListDeclField getField(){
        return champs;
    }

    public ListDeclMethod getMethod()
    {
        return methodes ;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("class ");
        nom.decompile(s);
        s.print(" extends ");
        superClasse.decompile(s);
        s.println(" {");
        s.indent();
        champs.decompile(s);
        methodes.decompile(s);
        s.unindent();
        s.println("}");

    }

    @Override
    protected void verifyClass(DecacCompiler compiler) throws ContextualError {

        Symbol symbolName = this.nom.getName();
        Symbol superName = this.superClasse.getName();

        TypeDefinition defSuper = compiler.environmentType.defOfType(superName);



        if(defSuper==null)
        {
            String description = String.format("classe %s n'est pas définie", superName.getName());
            throw new ContextualError(description, getLocation());
        }

        if(!defSuper.isClass())
        {
            String description = String.format("nature attendu du %s : classe ", superName.getName());
            throw new ContextualError(description, getLocation());
        }

        /*
         * le casting marche à cause de la verification précedant : isClass ClassDefinition
         */
        ClassType classtypeSuper = (ClassType)defSuper.getType();

        ClassType type = new ClassType(symbolName, getLocation(), classtypeSuper.getDefinition());

        if(compiler.environmentType.getMap().containsKey(symbolName))
        {
            String description = String.format("la classe %s est deja déclarée ", symbolName.getName());
            throw new ContextualError(description, getLocation());
        }
        else
        {
            compiler.environmentType.putdefType(symbolName, type.getDefinition());

            /* ----------------- décoration de definition -------------------- */
            this.superClasse.setDefinition(classtypeSuper.getDefinition());
            this.nom.setDefinition(type.getDefinition());


        }


    }

    @Override
    protected void verifyClassMembers(DecacCompiler compiler) throws ContextualError {
        /* ----------------- passe implémente régele (2.3) ---------------------- */
        Symbol symbolName = this.nom.getName();
        Symbol superName = this.superClasse.getName();

        // System.out.println("====== Decl Class ============");
        // System.out.println("je suis "+ symbolName.getName() + " mon super est = " + superName.getName());

        TypeDefinition defCurrentClasse = compiler.environmentType.defOfType(symbolName);
        TypeDefinition defSuperClasse = compiler.environmentType.defOfType(superName);

        if(!defCurrentClasse.getType().getName().equals(compiler.environmentType.Object.getName())) {

            if((defCurrentClasse==null || defSuperClasse==null) )
            {
                String description = "class non definie";
                throw new ContextualError(description, getLocation());
            }

            if(!defCurrentClasse.getType().isClass() || !defSuperClasse.getType().isClass())
            {
                String description = "nature attendu : class";
                throw new ContextualError(description, getLocation());
            }

        }



        /*
        * pour l'uitilisation du casting c'est bien justifiée car on a deja une verification : isClass()   
        */
        ClassType defTypeSuperClass = (ClassType)defSuperClasse.getType();
        ClassType defTypeCurrentClass = (ClassType)defCurrentClasse.getType();

        /*----------------------- index -------------------*/
        defTypeCurrentClass.getDefinition().setNumberOfMethods(defTypeSuperClass.getDefinition().getNumberOfMethods());
        defTypeCurrentClass.getDefinition().setNumberOfFields(defTypeSuperClass.getDefinition().getNumberOfFields());
        
        this.champs.verifyListDeclField(compiler , defTypeSuperClass.getDefinition() , defTypeCurrentClass.getDefinition());
        this.methodes.verifyListDeclMethod(compiler ,  defTypeSuperClass.getDefinition() , defTypeCurrentClass.getDefinition());


    }
    
    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
        TypeDefinition def = compiler.environmentType.defOfType(this.nom.getName());

        if(def==null || !def.isClass())
        {
            String description = "Erreur interne on doit pas arriver ici ";
            throw new ContextualError(description, getLocation());
        }

        ClassDefinition defClass = (ClassDefinition)def;
        EnvironmentExp env = defClass.getMembers();

        this.champs.verifyListFieldInitialization(compiler, env, defClass);
        this.methodes.verifyListDeclMethodBody(compiler, env, defClass);



    }


    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        nom.prettyPrint(s, prefix,false);
        superClasse.prettyPrint(s, prefix,false);
        champs.prettyPrint(s, prefix,false);
        methodes.prettyPrint(s, prefix,true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        nom.iter(f);
        superClasse.iter(f);
        champs.iter(f);
        methodes.iter(f);

    }

    @Override
    public void displayIndex(DecacCompiler compiler ) {

        TypeDefinition defCurrentClasse = compiler.environmentType.defOfType(this.nom.getName());
        ClassType defTypeCurrentClass = (ClassType)defCurrentClasse.getType();

        /*-------------- verification des index ---------------- */
        System.out.println("===================== " + this.nom.getName().getName() + " =====================");
        int NF = defTypeCurrentClass.getDefinition().getNumberOfFields();
        int NM = defTypeCurrentClass.getDefinition().getNumberOfMethods();


        System.out.println("NF = " + NF);
        System.out.println("NM = " + NM);
            
        defTypeCurrentClass.getDefinition().getMembers().displayIndex();


        System.out.println("============= La table des méthodes de "+ this.nom.getName().getName() + " ==================");
        
        // System.out.println(this.pileDeMethods().toString());
        MethodDefinition[]  pile = this.pileDeMethods();

        for(MethodDefinition def : pile) {
            System.out.println(def);
        }

    }


    @Override
    protected void codeGenClass(DecacCompiler compiler){
        compiler.addComment("---------------------------------------------------------");
        compiler.addComment("                 Classe " + nom.getName());
        compiler.addComment("---------------------------------------------------------");
        compiler.addComment("------- Initialisation des champs de "+nom.getName());

        //Initialisation des champs (attributs) de la classe
        Label initEtiquette = new Label("init."+nom.getName());
        nom.getClassDefinition().setInitEtiquette(initEtiquette);
        compiler.addLabel(initEtiquette);


        // TSTO et BOV ? besoin ou pas

        // Init a zero (codeGen de NoInit) avant d'init les champs hérités
        // et les champs non hérités à leurs valeurs données, nécéssaire?

        //Sauvegarde des registres
        RegisterManager.saveRegisters(compiler);


        // a ne pas faire quand la mere est object
        if (!superClasse.getName().getName().equals("Object")){
            compiler.addComment("Init des champs hérités");
            This current = new This(false);
            current.codeGenInst(compiler);
            compiler.addInstruction(new PUSH(Register.getR(RegisterManager.getNumR())));
            compiler.addInstruction(new BSR(superClasse.getClassDefinition().getInitEtiquette()));
            compiler.addInstruction(new SUBSP(1));
        }

        champs.codeGenListField(compiler);

        //Restauration des registres sauvegardés?
        RegisterManager.restoreRegisters(compiler);

        compiler.addInstruction(new RTS());

        compiler.addComment("Code des méthodes de la classe"+nom.getName());
        methodes.codeGenListMethod(compiler);

        RegisterManager.setNumR(2);
    }

    protected void tableEtiquetteMethode(){
        for (AbstractDeclMethod methodAbs: methodes.getList()) {
            DeclMethod method = (DeclMethod) methodAbs;
            String methodName = method.getMethodeName().getName().getName();
            Label label = new Label("code."+ nom.getName() + "." + methodName);
            method.getMethodeName().getMethodDefinition().setLabel(label);
        }
    }


    @Override
    protected void codeGenTableMethods(DecacCompiler compiler) {
        compiler.addComment("Code de la table de méthodes de la classe "+ nom.getName());

        DAddr opLea;
        if (superClasse.getName().getName().equals("Object")){
            opLea = new RegisterOffset(1, GB);
        }
        else{
            opLea = superClasse.getClassDefinition().getTableMethodeclass();
        }
        compiler.addInstruction(new LEA(opLea, R0));
        DAddr opMethod = new RegisterOffset(MethodDefinition.getOffset(), GB);
        compiler.addInstruction(new STORE(R0, opMethod));
        /*Sauvegarde de la table de methode dans ClassDefinition*/
        this.nom.getClassDefinition().setTableMethodeclass(opMethod);
        offsetSuper = MethodDefinition.getOffset();
        MethodDefinition.offsetIncr();

        LabelOperand equals = new LabelOperand(new Label("code.Object.equals"));
        compiler.addInstruction( new LOAD(equals , R0));
        DAddr gb2 = new RegisterOffset(MethodDefinition.getOffset(),GB);
        compiler.addInstruction(new STORE( R0,gb2));
        MethodDefinition.offsetIncr();


        for (MethodDefinition method : this.pileDeMethods()) {
            method.empileMethode(compiler);
        }



    }

    /**
     * return une pile qui contient l'ensemble de Def du methode 
     */
    public  MethodDefinition[]  pileDeMethods() {

        int nb = this.nom.getClassDefinition().getNumberOfMethods();
        MethodDefinition[]  pile = this.nom.getClassDefinition().getMembers().getPileMethode(nb);
        return pile ;
        
    }

}
