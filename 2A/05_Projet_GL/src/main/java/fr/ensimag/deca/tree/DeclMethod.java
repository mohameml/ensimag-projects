package fr.ensimag.deca.tree;


import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.util.Util;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.*;


/**
 * 
 * @author gl10
 * @date 12/01/2024
 */

public class DeclMethod extends AbstractDeclMethod {

    private AbstractIdentifier type ;
    private AbstractIdentifier methodeName ;
    private ListDeclParam params ;
    private AbstractMethodBody body ;



    public AbstractIdentifier getMethodeName() {
        return methodeName;
    }



    public DeclMethod(AbstractIdentifier type , AbstractIdentifier methodeName  , ListDeclParam params , AbstractMethodBody body )
    {
        Validate.notNull(type);
        Validate.notNull(methodeName);
        Validate.notNull(params);
        Validate.notNull(body);

        this.type = type ;
        this.methodeName = methodeName ;
        this.params = params ;
        this.body = body;
    }

    @Override
    protected  void verifyDeclMethod(DecacCompiler compiler , ClassDefinition superclasse , ClassDefinition currentclasse  ) throws ContextualError 
    {
        /*
         * 
         * verification de déclaration des methodes pour la passe 2  : régle (2.7 )
         */

        Type typeMethod = this.type.verifyType(compiler);

        Signature sig = params.verifyListDeclParam(compiler);



        /*
         * Si une méthode est redéfinie, alors celle-ci :
                — doit avoir la même signature que la méthode héritée ;
                — doit avoir pour type de retour un sous-type du type de retour de la méthode héritée. 
         */


        boolean isRedefine = false ; 
        if(superclasse.getMembers().contains(methodeName.getName()))
        {
            isRedefine = true ; 
            ExpDefinition def = superclasse.getMembers().get(methodeName.getName());

            /*---------------------- elle doit etre une methode ----------------- */

            if(!def.isMethod())
            {
                String description = String.format("une rédefinition inacceptable  du %s en tant que methode ",this.methodeName.getName().getName());
                throw new ContextualError(description, getLocation()); 
            }


            /*-------------- doit avoir la même signature que la méthode héritée ; --------------------  */
            MethodDefinition defMethode = (MethodDefinition)def ;
            
            Signature sig2 = defMethode.getSignature();


            if(!sig2.equals(sig))
            {
                String description = "une méthode rédefine doit avoir la même signature que la méthode héritée ";
                throw new ContextualError(description, getLocation());
            }

            /*-------------------------- doit avoir pour type de retour un sous-type du type de retour de la méthode héritée.  -------------- */
            Type type2 = defMethode.getType();

            if(!Util.subType(compiler, typeMethod, type2))
            {
                String description = String.format("le %s n'est pas un sous type du %s  ", typeMethod.getName().getName() , type2.getName().getName());
                throw new ContextualError(description, getLocation());

            }
            
        }

        try 
        {
            /*----------- déclaration du def dans currentclasse : -------------- */
            int index ;

            if(isRedefine) {
                ExpDefinition def = superclasse.getMembers().get(methodeName.getName());
                MethodDefinition defMethode = (MethodDefinition)def ;
                index = defMethode.getIndex() ;
            }
            else {
                index = currentclasse.getNumberOfMethods() + 1 ;
                currentclasse.setNumberOfMethods(index);
            }

            MethodDefinition defMet = new MethodDefinition(typeMethod, getLocation(), sig, index);
            currentclasse.getMembers().declare(this.methodeName.getName(), defMet);

            /*---------------------- décoreation --------------------- */
            this.methodeName.setDefinition(defMet);
            this.type.setDefinition(compiler.environmentType.defOfType(typeMethod.getName()));
            
        }
        catch(DoubleDefException e)
        {
            String description = String.format("%s est déjà déclarée", this.methodeName.getName().getName());
            throw new ContextualError(description, getLocation());
        }





    }

    @Override
    protected void verifyDeclMethodBody(DecacCompiler compiler , EnvironmentExp EnvExp , ClassDefinition Currentclass ) throws ContextualError {

        Type typeReturn = this.type.verifyType(compiler);

        EnvironmentExp env = new EnvironmentExp(EnvExp);
        this.params.verifyListParamBody(compiler, env);

        this.body.verifyMethodBody(compiler, env, Currentclass, typeReturn);

    }

    @Override
    public void decompile(IndentPrintStream s) {
        type.decompile(s);
        s.print(' ');
        methodeName.decompile(s);
        s.print('(');
        params.decompile(s);
        s.print(')');
        body.decompile(s);

    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        
        type.prettyPrint(s, prefix, false);
        methodeName.prettyPrint(s, prefix, false);
        params.prettyPrint(s, prefix, false);
        body.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        methodeName.iter(f);
        params.iter(f);
        body.iter(f);

    }



    @Override
    protected void codeGenMethod(DecacCompiler compiler) {

        Label etiq = this.getMethodeName().getMethodDefinition().getLabel();
        compiler.addLabel(etiq);

        //TSTO et BOV et ADDSP à mettre  ----

        //Sauvegarde des registres
        RegisterManager.saveRegisters(compiler);
        
        params.codeGenListParam(compiler);


        body.codeGenMethod(compiler);


        Label etiqfin = new Label("fin."+ getMethodeName().getMethodDefinition().getLabel());

        if (!type.getName().equals("void")) { // marche pas, la condition renvoie faux pour type de retour = void
            compiler.addInstruction(new BRA(etiqfin));
            compiler.addInstruction(new WSTR("Erreur : sortie de la methode " + getMethodeName().getMethodDefinition().getLabel() + " sans return"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }


        compiler.addLabel(etiqfin);
        
        // Restauration des registres
        RegisterManager.restoreRegisters(compiler);


        compiler.addInstruction(new RTS());

    }



    @Override
    protected void empileMethode(DecacCompiler compiler) {
        // rien 
    }







}
