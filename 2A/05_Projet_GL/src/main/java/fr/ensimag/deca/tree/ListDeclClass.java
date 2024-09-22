package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;
import org.apache.log4j.Logger;

import static fr.ensimag.ima.pseudocode.Register.*;
import static fr.ensimag.ima.pseudocode.Register.R1;

/**
 *
 * @author gl10
 * @date 01/01/2024
 */
public class ListDeclClass extends TreeList<AbstractDeclClass> {
    private static final Logger LOG = Logger.getLogger(ListDeclClass.class);
    private Label etiq_equals;

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclClass c : getList()) {
            c.decompile(s);
            s.println();
        }
    }

    /**
     * Pass 1 of [SyntaxeContextuelle]
     */
    void verifyListClass(DecacCompiler compiler) throws ContextualError {
        // LOG.debug("verify listClass: start");

        /* --------------- passe 1 --------------------- */
        for(AbstractDeclClass c : this.getList())
        {   
            c.verifyClass(compiler);

        }

        // LOG.debug("verify listClass: end");
    }

    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    public void verifyListClassMembers(DecacCompiler compiler) throws ContextualError {

        /* --------------- passe 2 --------------------- */
        for(AbstractDeclClass c : this.getList())
        {   
            c.verifyClassMembers(compiler);
        }
    }
    
    /**
     * Pass 3 of [SyntaxeContextuelle]
     */
    public void verifyListClassBody(DecacCompiler compiler) throws ContextualError {

        /* --------------- passe 3 --------------------- */
        for(AbstractDeclClass c : this.getList())
        {   
            c.verifyClassBody(compiler);
        }

    }
    /**
    * Passe 1 of [genCode]*/
    protected void codeGenTableMethods(DecacCompiler compiler){
        /*Partie 1 passe 1*/
        for (AbstractDeclClass declClassAbs : getList()){
            DeclClass declClass = (DeclClass)declClassAbs;
            declClass.tableEtiquetteMethode();
        }

        /*Partie 2 passe 1 dans une autre boucle ?*/
        /*debut Table de methode de Object*/
        compiler.addComment("Code de la table des méthodes de Object");
        compiler.addInstruction(new LOAD(new NullOperand(), R0));
        DAddr gb1 = new RegisterOffset(1,GB);
        compiler.addInstruction(new STORE( R0,gb1));
        Label equals_etiq = new Label("code.Object.equals");
        this.etiq_equals = equals_etiq;
        LabelOperand equals = new LabelOperand(equals_etiq);
        compiler.addInstruction( new LOAD(equals , R0));
        DAddr gb2 = new RegisterOffset(2,GB);
        compiler.addInstruction(new STORE( R0,gb2));
        /* fin Table de methode de Object*/

        for (AbstractDeclClass declClassAbs : getList()){
            DeclClass declClass = (DeclClass)declClassAbs;
            declClass.codeGenTableMethods(compiler);
        }

    }

    protected void codeGenClasses(DecacCompiler compiler){
        for (AbstractDeclClass declClass : getList()){
            declClass.codeGenClass(compiler);
        }
    }

    /**
     * pour la verification de index 
     */
    public void displayIndex(DecacCompiler compiler) {
        
        for(AbstractDeclClass c : this.getList())
        {
            c.displayIndex(compiler);
        }
    }

    protected void codeGenObject(DecacCompiler compiler){
        /* Decl de classe Object a faire  */
        compiler.addComment("-----------------------------------------------");
        compiler.addComment("                   Classe Object");
        compiler.addComment("-----------------------------------------------");
        compiler.addLabel(etiq_equals);
        DAddr op_this = new RegisterOffset(-2 , LB);
        DAddr op_other = new RegisterOffset(-3 , LB);
        compiler.addInstruction(new LOAD(op_this, R0));
        compiler.addInstruction(new LOAD(new RegisterOffset(0,R0), R0));
        compiler.addInstruction(new LOAD(op_other, R1));
        compiler.addInstruction(new LOAD(new RegisterOffset(0,R1), R1));
        compiler.addInstruction(new CMP(R0, R1));
        compiler.addInstruction(new SEQ(R0));
        compiler.addInstruction(new RTS());
    }

}
