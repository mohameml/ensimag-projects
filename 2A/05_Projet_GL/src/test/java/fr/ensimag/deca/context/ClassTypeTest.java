package fr.ensimag.deca.context;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.Location;

public class ClassTypeTest {

    @Test
    public void testMethodeSuClass()
    {
        DecacCompiler compiler = new DecacCompiler(null, null);
        Symbol ASymb = compiler.createSymbol("A");
        Symbol BSymb  =  compiler.createSymbol("B");



        ClassType A = new ClassType(ASymb, Location.BUILTIN, compiler.environmentType.Object.getDefinition());
        ClassType B = new ClassType(BSymb,Location.BUILTIN, A.getDefinition());

        assertTrue(A.isSubClassOf(A));
        assertTrue(A.isSubClassOf(compiler.environmentType.Object));
        assertTrue(A.getDefinition().getSuperClass().getType().getName().equals(compiler.environmentType.Object.getName()));
        assertFalse(A.isSubClassOf(B));
        // assertFalse(A.isSubClassOf(nullType));

        assertTrue(B.isSubClassOf(A));
        assertTrue(B.isSubClassOf(compiler.environmentType.Object));
        assertTrue(B.isSubClassOf(B));
        




        
    }

    
}
