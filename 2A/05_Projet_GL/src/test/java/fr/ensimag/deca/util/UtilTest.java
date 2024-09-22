package fr.ensimag.deca.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.FloatType;

import fr.ensimag.deca.context.IntType;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tree.*;


public class UtilTest {

    @Test
    public void TestSubType()
    {
        DecacCompiler complier = new DecacCompiler(null, null);
        
        IntType int1 = complier.environmentType.INT;
        FloatType float1 = complier.environmentType.FLOAT;

        // int n'est pas un sous type de float 
        assertFalse(Util.subType(complier, int1, float1));
    }


    @Test
    public void TestAssingCompatible()
    {
        DecacCompiler complier = new DecacCompiler(null, null);
        
        IntType int1 = complier.environmentType.INT;
        FloatType float1 = complier.environmentType.FLOAT;

        // int peut etre affecte à Float  
        assertTrue(Util.assignCompatibe(complier, float1, int1));

        // on peut affecté int dans int 
        assertTrue(Util.assignCompatibe(complier, int1, int1));

        // on peut affecté float dans float 
        assertTrue(Util.assignCompatibe(complier, int1, int1));
    
    }
    

    @Test 
    public void TestCastCompatible()
    {
        DecacCompiler complier = new DecacCompiler(null, null);


        // un int peut etre convertir à un int 
        assertTrue(Util.castCompatibel(complier, complier.environmentType.INT, complier.environmentType.INT));

        // un int peut etre convertir à un float 
        assertTrue(Util.castCompatibel(complier, complier.environmentType.INT, complier.environmentType.FLOAT));

        // un Flaot peut etre convertir à un int  
        assertTrue(Util.castCompatibel(complier, complier.environmentType.FLOAT, complier.environmentType.INT));

    }
    

    @Test
    public void TestTypeUnaryOp()
    {
        DecacCompiler complier = new DecacCompiler(null, null);


        
        // minus , int --> int 
        AbstractExpr expr = new UnaryMinus(new IntLiteral(10));
        Type typeOp = Util.type_unaru_op(complier, expr, complier.environmentType.INT);
        assertTrue(typeOp.isInt());

        // minus , float --->  float 
        AbstractExpr expr2 = new UnaryMinus(new FloatLiteral(10));
        Type typeOp2 = Util.type_unaru_op(complier, expr2, complier.environmentType.FLOAT);        
        assertTrue(typeOp2.isFloat());

        // Not , Boolean --->  Boolean 
        AbstractExpr expr3 = new Not(new BooleanLiteral(true));
        Type typeOp3 = Util.type_unaru_op(complier, expr3, complier.environmentType.BOOLEAN);        
        assertTrue(typeOp3.isBoolean());

    }


    @Test
    public void TestTypeArthiOp()
    {
        DecacCompiler complier = new DecacCompiler(null, null);
        
        // int , int ---> int 
        AbstractExpr expr1 = new Minus(new IntLiteral(10) , new IntLiteral(10));
        Type typeOp1 = Util.type_binary_op(complier, expr1 , complier.environmentType.INT, complier.environmentType.INT);
        assertTrue(typeOp1.isInt());

        // float , float ---> float 
        AbstractExpr expr2 = new Plus(new FloatLiteral(10) , new FloatLiteral(10));
        Type typeOp2 = Util.type_binary_op(complier, expr2 , complier.environmentType.FLOAT, complier.environmentType.FLOAT);
        assertTrue(typeOp2.isFloat());

        // int , float ---> int 
        AbstractExpr expr3 = new Divide(new IntLiteral(10) , new FloatLiteral(10));
        Type typeOp3 = Util.type_binary_op(complier, expr3 , complier.environmentType.INT, complier.environmentType.FLOAT);
        assertTrue(typeOp3.isFloat());

        // float , int ---> int 
        AbstractExpr expr4 = new Multiply(new FloatLiteral(10) , new IntLiteral(10));
        Type typeOp4 = Util.type_binary_op(complier, expr4 , complier.environmentType.FLOAT, complier.environmentType.INT);
        assertTrue(typeOp4.isFloat());

        
        
    }


    @Test 
    public void TestTypeBinaryOp()
    {
        DecacCompiler complier = new DecacCompiler(null, null);

        // On test le type_arthi_op :
        TestTypeArthiOp();

        // mod , int ,int ---> int 
        AbstractExpr expr1 = new Modulo(new IntLiteral(10) , new IntLiteral(10));
        Type typeOp1 = Util.type_binary_op(complier, expr1 , complier.environmentType.INT, complier.environmentType.INT);
        assertTrue(typeOp1.isInt());

        // EQ , INT , INT ---> boolean 
        AbstractExpr expr2 = new Equals(new IntLiteral(10) , new IntLiteral(10));
        Type typeOp2 = Util.type_binary_op(complier, expr2 , complier.environmentType.INT, complier.environmentType.INT);
        assertTrue(typeOp2.isBoolean());

        // NEQ , FLAOT , INT ---> boolean 
        AbstractExpr expr3 = new NotEquals(new FloatLiteral(10) , new IntLiteral(10));
        Type typeOp3 = Util.type_binary_op(complier, expr3 , complier.environmentType.FLOAT, complier.environmentType.INT);
        assertTrue(typeOp3.isBoolean());

        // GT , INT , FLAOT ---> boolean 
        AbstractExpr expr4 = new Greater(new IntLiteral(10) , new FloatLiteral(10));
        Type typeOp4 = Util.type_binary_op(complier, expr4 , complier.environmentType.INT, complier.environmentType.FLOAT);
        assertTrue(typeOp4.isBoolean());


        // AND , Boolean , Boolean  ---> boolean 
        AbstractExpr expr5 = new And(new BooleanLiteral(false) , new BooleanLiteral(false));
        Type typeOp5 = Util.type_binary_op(complier, expr5 , complier.environmentType.BOOLEAN, complier.environmentType.BOOLEAN);
        assertTrue(typeOp5.isBoolean());


    }



}
