package fr.ensimag.deca.context;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tools.SymbolTable.Symbol;

public class BooleanTypeTest {


    @Test
    void boolAndAnotherBollAreThesameType()
    {
        SymbolTable table  = new SymbolTable();

        Symbol s1 = table.create("boolean");
        Symbol s2 = table.create("Anotherboolean");

        BooleanType bool1 = new BooleanType(s1);
        BooleanType bool2 = new BooleanType(s2);


        assertTrue(bool1.sameType(bool2));        
    
    }


    @Test
    void boolAndIntAreNotThesameType()
    {
        SymbolTable table  = new SymbolTable();

        Symbol s1 = table.create("boolean");
        Symbol s2 = table.create("int");

        BooleanType bool1 = new BooleanType(s1);
        IntType i = new IntType(s2);


        assertFalse(bool1.sameType(i));        
    
    }
    

    @Test
    void boolAndStringAreNotThesameType()
    {
        SymbolTable table  = new SymbolTable();

        Symbol s1 = table.create("boolean");
        Symbol s2 = table.create("int");

        BooleanType bool1 = new BooleanType(s1);
        StringType str = new StringType(s2);


        assertFalse(bool1.sameType(str));        
    
    }
    
}
