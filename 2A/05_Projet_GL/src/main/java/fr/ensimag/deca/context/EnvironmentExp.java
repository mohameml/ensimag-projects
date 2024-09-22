package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.Location;


import java.util.HashMap;
import java.util.Iterator;

import java.util.Map;

import java.util.Map.Entry;

/**
 * Dictionary associating identifier's ExpDefinition to their names.
 * 
 * This is actually a linked list of dictionaries: each EnvironmentExp has a
 * pointer to a parentEnvironment, corresponding to superblock (eg superclass).
 * 
 * The dictionary at the head of this list thus corresponds to the "current" 
 * block (eg class).
 * 
 * Searching a definition (through method get) is done in the "current" 
 * dictionary and in the parentEnvironment if it fails. 
 * 
 * Insertion (through method declare) is always done in the "current" dictionary.
 * 
 * @author gl10
 * @date 01/01/2024
 */
public class EnvironmentExp {


    /**
     * c'est le parent des  suivantes 
     */
    EnvironmentExp parentEnvironment  ;

    public EnvironmentExp getParenrt()
    {
        return this.parentEnvironment;
    }

    /**
     * map : pour stocker l' association nom -> définition
     */

    private Map<Symbol , ExpDefinition> map ;

    /**
     * 
     * @param parentEnvironment : l'environnement courant
     */

    public EnvironmentExp(EnvironmentExp parentEnvironment) {


        this.map = new HashMap<>();
        this.parentEnvironment = parentEnvironment;

    }


    public Map<Symbol , ExpDefinition> getMap()
    {
        return this.map;
    }


    public static class DoubleDefException extends Exception {
        private static final long serialVersionUID = -2733379901827316441L;

        public DoubleDefException(String msg)
        {
            super(msg);
        }
    }

    /**
     * Return the definition of the symbol in the environment, or null if the
     * symbol is undefined.
     */
    public ExpDefinition get(Symbol key) {
        EnvironmentExp parent = this;
        while(parent!=null)
        {
            Map<Symbol , ExpDefinition> map = parent.getMap();            
            if(map.containsKey(key))
            {
                ExpDefinition def =  map.get(key);    
                
                return def ;
            }
            else 
            {
                parent = parent.getParenrt();
            }
        }

        return null ;

    }

    /**
     * Add the definition def associated to the symbol name in the environment.
     * 
     * Adding a symbol which is already defined in the environment,
     * - throws DoubleDefException if the symbol is in the "current" dictionary 
     * - or, hides the previous declaration otherwise.
     * 
     * @param name
     *            Name of the symbol to define
     * @param def
     *            Definition of the symbol
     * @throws DoubleDefException
     *             if the symbol is already defined at the "current" dictionary
     *
     */
    public void declare(Symbol name, ExpDefinition def) throws DoubleDefException {
        
        Map<Symbol , ExpDefinition> envExp = this.getMap();

        // verifier que Symbol name n'est pas encore déclarée :

        if(envExp.containsKey(name))
        {
            Location loc = def.getLocation();
            String messageErr = String.format("%s:%s:%s: %s est déjà déclarée" ,loc.getFilename() , loc.getLine() , loc.getPositionInLine() , name );

            throw new DoubleDefException(messageErr);
        }
        else
        {
            envExp.put(name , def);
        }
        

    }


    /**
     * indique si une méthode ou un champ est define dans un super class 
     * @param key
     * @return
     */
    public boolean contains(Symbol key) {

     
        EnvironmentExp parent = this;
        while(parent!=null)
        {
         

            if(parent.map.containsKey(key))
            {
                return true ;
            }
            else {
                parent = parent.getParenrt();
            }
        }

        return false ;
    }


    public void displayIndex() {

        StringBuffer strFields = new StringBuffer();
        StringBuffer strMethodes = new StringBuffer();

        this.displayIndexField(strFields);
        this.displayIndexMethod(strMethodes);

        System.out.print("---------------- Fields ------------------");
        System.out.println(strFields);

        System.out.print("---------------- Methods ------------------");
        System.out.println(strMethodes);

    }

    public void displayIndexField(StringBuffer strFields) {

        if(this.parentEnvironment != null) {
            this.parentEnvironment.displayIndexField(strFields);
        }

        Map<Symbol , ExpDefinition> map = this.getMap();

        Iterator<Entry<Symbol , ExpDefinition>> itr = map.entrySet().iterator();

        while(itr.hasNext()) {
            Entry<Symbol , ExpDefinition> entry = itr.next();
            ExpDefinition def = entry.getValue() ;
            if(def.isField()) {
                FieldDefinition defFiedl = (FieldDefinition)def ;
                strFields.append("\n"+entry.getKey()+" --> " + defFiedl.getIndex());
            }

        }

    }


    public void displayIndexMethod(StringBuffer strMethodes) {

        if(this.parentEnvironment != null) {
            this.parentEnvironment.displayIndexMethod(strMethodes);
        }

        Iterator<Entry<Symbol , ExpDefinition>> itr = map.entrySet().iterator();

        while(itr.hasNext()) {
            Entry<Symbol , ExpDefinition> entry = itr.next();
            ExpDefinition def = entry.getValue() ;

            if(def.isMethod()) {
                MethodDefinition defMethod = (MethodDefinition)def ;
                strMethodes.append("\n"+entry.getKey()+" --> " + defMethod.getIndex());

            }
        }
    }


    public MethodDefinition[] getPileMethode(int nb) {

        MethodDefinition[] list = new MethodDefinition[nb] ; 

        this.addPileMethod(list);



        return list ; 

    }

    public void addPileMethod(MethodDefinition[] list) {

        if(this.parentEnvironment != null) {
            this.parentEnvironment.addPileMethod(list);
        }

        Iterator<Entry<Symbol , ExpDefinition>> itr = map.entrySet().iterator();

        while(itr.hasNext()) {
            Entry<Symbol , ExpDefinition> entry = itr.next();
            ExpDefinition def = entry.getValue() ;

            if(def.isMethod()) {
                MethodDefinition defMethod = (MethodDefinition)def ;
                list[defMethod.getIndex() - 1] = defMethod;

            }
        }

    }




}
