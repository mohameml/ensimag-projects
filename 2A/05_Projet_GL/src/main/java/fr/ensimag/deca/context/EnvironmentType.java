package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.Location;

import java.util.HashMap;
import java.util.Map;

// A FAIRE: étendre cette classe pour traiter la partie "avec objet" de Déca
/**
 * Environment containing types. Initially contains predefined identifiers, more
 * classes can be added with declareClass().
 *
 * @author gl10
 * @date 01/01/2024
 */
public class EnvironmentType {
    public EnvironmentType(DecacCompiler compiler) {
        
        envTypes = new HashMap<Symbol, TypeDefinition>();
        
        Symbol intSymb = compiler.createSymbol("int");
        INT = new IntType(intSymb);
        envTypes.put(intSymb, new TypeDefinition(INT, Location.BUILTIN));

        Symbol floatSymb = compiler.createSymbol("float");
        FLOAT = new FloatType(floatSymb);
        envTypes.put(floatSymb, new TypeDefinition(FLOAT, Location.BUILTIN));

        Symbol voidSymb = compiler.createSymbol("void");
        VOID = new VoidType(voidSymb);
        envTypes.put(voidSymb, new TypeDefinition(VOID, Location.BUILTIN));

        Symbol booleanSymb = compiler.createSymbol("boolean");
        BOOLEAN = new BooleanType(booleanSymb);
        envTypes.put(booleanSymb, new TypeDefinition(BOOLEAN, Location.BUILTIN));

        Symbol stringSymb = compiler.createSymbol("string");
        STRING = new StringType(stringSymb);
        // not added to envTypes, it's not visible for the user.


        /*----------------- Partie Objet ----------------- */
        Symbol  objectSymbol = compiler.createSymbol("Object");
        Object = new ClassType(objectSymbol , Location.BUILTIN , null);
        
        
        Symbol  equalsSymbol = compiler.createSymbol("equals");
        Signature sig = new Signature();
        sig.add(Object);
        MethodDefinition equalsDef = new MethodDefinition(BOOLEAN, Location.BUILTIN, sig, 1) ; // equals ---> index 1 
        Object.getDefinition().setNumberOfMethods(1);
        try {
            
            Object.getDefinition().getMembers().declare(equalsSymbol, equalsDef);
        } catch (Exception e) {
            e.printStackTrace();   
        }

        envTypes.put(objectSymbol, Object.getDefinition());
        
    }

    private final Map<Symbol, TypeDefinition> envTypes;

    public Map<Symbol, TypeDefinition> getMap()
    {
        return this.envTypes;
    }

    public TypeDefinition defOfType(Symbol s) {
        return envTypes.get(s);
    }

    public void putdefType(Symbol name ,TypeDefinition def)
    {
        envTypes.put(name, def);
    }

    public final VoidType    VOID;
    public final IntType     INT;
    public final FloatType   FLOAT;
    public final StringType  STRING;
    public final BooleanType BOOLEAN;

    public final ClassType Object ;
}
