package fr.ensimag.deca.context;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.Location;


public class EnvironmentExpTest {


    @Test
    public void definitionDUneVaribleXDeuxfois()
    {
        /*
         * Test : 
         *  {
         *      int x = 1 ;
         *      int x = 2; 
         *  }
         */
        
        SymbolTable table = new SymbolTable();

        Symbol s1 = table.create("x");
        Symbol s2 = table.create("x");

        IntType i1 = new IntType(s1);
        IntType i2 = new IntType(s2);

         
        EnvironmentExp envExp = new EnvironmentExp(null);

        assertThrows(EnvironmentExp.DoubleDefException.class, () -> 
        {
            envExp.declare(s1, new VariableDefinition(i1 , new Location(2, 2, "test.deca")));
            envExp.declare(s2, new VariableDefinition(i2 , new Location(3, 2, "test.deca")));
        });

    
    }
    

    




    @Test 
    public void TestEnvExpr()
    {
        SymbolTable table = new SymbolTable();
        DecacCompiler compiler = new DecacCompiler(null, null);

        EnvironmentExp envExp = new EnvironmentExp(null);


        Symbol symobl0fx = table.create("x");


        try {
            envExp.declare(symobl0fx, new VariableDefinition(compiler.environmentType.INT, new Location(0, 0, "test.deca")));
        } catch (DoubleDefException e) {

            e.printStackTrace();
        }

        assertTrue(envExp.getMap().containsKey(symobl0fx));
        
        ExpDefinition def = envExp.get(symobl0fx);

        assertTrue(def!=null);
    }
}
