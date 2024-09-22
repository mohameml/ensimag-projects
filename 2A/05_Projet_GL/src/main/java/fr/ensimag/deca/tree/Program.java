package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.*;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import java.io.PrintStream;

/**
 * Deca complete program (class definition plus main block)
 *
 * @author gl10
 * @date 01/01/2024
 */
public class Program extends AbstractProgram {
    private static final Logger LOG = Logger.getLogger(Program.class);
    
    public Program(ListDeclClass classes, AbstractMain main) {
        Validate.notNull(classes);
        Validate.notNull(main);
        this.classes = classes;
        this.main = main;
    }
    public ListDeclClass getClasses() {
        return classes;
    }
    public AbstractMain getMain() {
        return main;
    }
    private ListDeclClass classes;
    private AbstractMain main;
    @Override
    public void verifyProgram(DecacCompiler compiler) throws ContextualError {
        //LOG.debug("verify program: start");

        /*------------- passe 1   ---------------- */
        this.classes.verifyListClass(compiler);

        /* ------------ passe 2 ------------------- */
        this.classes.verifyListClassMembers(compiler);


        /* ------------- passe 3 : ----------------- */

        this.classes.verifyListClassBody(compiler);

        this.main.verifyMain(compiler);

       // LOG.debug("verify program: end");
    }

    @Override
    public void codeGenProgram(DecacCompiler compiler) {
        boolean nocheck = compiler.getCompilerOptions().getNocheck();
        Label pile_pleine = new Label("pile_pleine");
        if (!nocheck){
            compiler.addInstruction(new BOV(pile_pleine));
        }
        
        classes.codeGenTableMethods(compiler);
        DeclVar.setI(MethodDefinition.getOffset());
        int lenADDSP = main.lenVarGlob() + DeclVar.getI() - 1;
        compiler.addFirst(new ADDSP( lenADDSP ));

        /*Le programme principal vient avant la decl des classes*/
        main.codeGenMain(compiler);



        compiler.addInstruction(new HALT());

        compiler.addLabel(pile_pleine);
        compiler.addInstruction(new WSTR("Erreur : Debordement de la pile"));
        compiler.addInstruction(new WNL());
        compiler.addInstruction(new ERROR());

        compiler.addLabel(new Label("tas_plein"));
        compiler.addInstruction(new WSTR("Erreur : Debordement de la pile"));
        compiler.addInstruction(new WNL());
        compiler.addInstruction(new ERROR());

        compiler.addLabel(new Label("dereferencement.null"));
        compiler.addInstruction(new WSTR("Erreur : dereferencement de null"));
        compiler.addInstruction(new WNL());
        compiler.addInstruction(new ERROR());

        compiler.addLabel(new Label("Division_par_zero"));
        compiler.addInstruction(new WSTR("Erreur : Division par zero"));
        compiler.addInstruction(new WNL());
        compiler.addInstruction(new ERROR());

        compiler.addLabel(new Label("Debordement_arithmetique"));
        compiler.addInstruction(new WSTR("Erreur : calcul arithmetique debordant"));
        compiler.addInstruction(new WNL());
        compiler.addInstruction(new ERROR());

        compiler.addLabel(new Label("Modulo_zero"));
        compiler.addInstruction(new WSTR("Erreur : Reste de la division entière par 0"));
        compiler.addInstruction(new WNL());
        compiler.addInstruction(new ERROR());

        compiler.addLabel(new Label("Max_variable_temp"));
        compiler.addInstruction(new WSTR("Erreur : L'expression de l'expression fait recours à trop de variables temporaires"));
        compiler.addInstruction(new WNL());
        compiler.addInstruction(new ERROR());

        /*L'init des var glob commence après la table de methodes*/
        classes.codeGenObject(compiler);
        classes.codeGenClasses(compiler);


    }

    @Override
    public void decompile(IndentPrintStream s) {
        getClasses().decompile(s);
        getMain().decompile(s);
    }
    
    @Override
    protected void iterChildren(TreeFunction f) {
        classes.iter(f);
        main.iter(f);
    }
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        classes.prettyPrint(s, prefix, false);
        main.prettyPrint(s, prefix, true);
    }
    @Override
    public void displayIndex(DecacCompiler compiler) {
        this.classes.displayIndex(compiler);
    }
}
