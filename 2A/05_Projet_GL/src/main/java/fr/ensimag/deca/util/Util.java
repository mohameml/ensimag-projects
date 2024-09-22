package fr.ensimag.deca.util;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tree.AbstractExpr;

/**
 * @author gl10 
 * 
 * package pour les fonctions utilitaires 
 */

public class Util{



    /**
     * Relation de sous-typage :  
     * @param compiler contient "env_types"
     * @param type1  
     * @param type2
     * @return  boolean : indique si type1 est un sous type de type2
     
     */
    
    public static boolean subType(DecacCompiler compiler , Type type1 , Type type2)
    {
        boolean bool = false ;

        if(type1.sameType(type2))
        {
            // — Pour tout type T, T est un sous-type de T.     
            bool = true ;
        }
        else if(type1.isClass() && type2.getName().equals(compiler.environmentType.Object.getName()))
        {
             // — Pour toute classe A, type_class(A) est un sous-type de type_class(Object)
            bool = true ;
        }
        else if(type1.isClass() && type2.isClass())
        {
            // Si une classe B étend une classe A dans l’environnement env, alors type_class(B) est un sous-type de type_class(A).

            // Si une classe C étend une classe B dans l’environnement env et si type_class(B) est un sous-type
            // de T, alors type_class(C) est un sous-type de T 
            
            ClassType type1Class =(ClassType)type1;
            ClassType type2Class =(ClassType)type2;

            if(type1Class.isSubClassOf(type2Class))
            {
                bool = true ;
            }
 
        }
        else if(type1.isNull() && type2.isClass())
        {
            // Pour toute classe A, null est un sous-type de type_class(A)
            bool = true ;
        }




        return bool ;

    }

    /**
     *  Compatibilité pour l’affectation : 
     * @param compiler
     * @param type1
     * @param type2
     * @return boolean : indique si type2 peut etre affecte à type1
     */

    public static  boolean  assignCompatibe(DecacCompiler compiler , Type type1 , Type type2)   
    {
        if(type1.isFloat() && type2.isInt())
        {
            return true ;
        }

        return Util.subType(compiler, type2, type1);

    }

    /**
     *  Compatibilité pour la conversion : 
     * @param compiler
     * @param op
     * @param type
     * @return boolean : qui indique est ce que le type1 peut etre convertir à type2
     * 
     */
    
    public static boolean castCompatibel(DecacCompiler compiler , Type type1 , Type type2)
    {
        if(type1.isVoid())
        {
            return false ;
        }

        
        return assignCompatibe(compiler, type1, type2) || assignCompatibe(compiler, type2, type1);
        
    }


    /**
     *  @return : return le type d'une opération Uniare 
     */

    public static Type type_unaru_op(DecacCompiler compiler , AbstractExpr op , Type type)
    {
        if(op.isMinus() && type.isInt())
        {
            return compiler.environmentType.INT;
        }
        else if(op.isMinus() && type.isFloat())
        {
            return compiler.environmentType.FLOAT;
        }
        else if(op.isNOT() && type.isBoolean())
        {
            return compiler.environmentType.BOOLEAN;
        }

        /* en cas d'erreur */
        return null ;
    }

    /**
     *  @return : return le type d'une opération binaire arthimétique 
     */
    public static  Type type_arith_op(DecacCompiler compiler ,Type t1 , Type t2)
    {
        if(t1.isInt() && t2.isInt())
        {
            return compiler.environmentType.INT;
        }
        else if(t1.isFloat() && t2.isFloat())
        {
            return compiler.environmentType.FLOAT;
        }
        else if(t1.isFloat() && t2.isInt())
        {
            return compiler.environmentType.FLOAT;
        }
        else if(t1.isInt() && t2.isFloat())
        {
            return compiler.environmentType.FLOAT;

        }


        /* en cas d'erreur */
        return null ;
    }
    /**
     *  @return : return le type d'une opération binaire  
     */

    public static  Type type_binary_op(DecacCompiler complier , AbstractExpr op , Type t1 , Type t2)
    {
        if(op.isPlus() || op.isMinus() || op.isMult() || op.isDivide() )
        {
            return type_arith_op(complier, t1, t2);
        }
        else if(op.isMod() && t1.isInt() && t2.isInt())
        {
            return complier.environmentType.INT;
        }
        else if( (op.isEQ() || op.isNEQ() || op.isLT() || op.isGT() || op.isLEQ() || op.isGEQ() )
            && ( (t1.isInt() || t1.isFloat()) && (t2.isInt() || t2.isFloat()) ) ) 
        {
            // eq, neq, lt, gt, leq, geq && t (T1, T2) ∈ dom(type_arith_op)
            return complier.environmentType.BOOLEAN;
        }
        else if( (op.isAnd() || op.isOR() || op.isEQ() || op.isNEQ() ) && (t1.isBoolean() && t2.isBoolean()))
        {
            return complier.environmentType.BOOLEAN;

        }
        else if((op.isEQ() || op.isNEQ()) && t1.isClassOrNull() && t2.isClassOrNull() )
        {

            /*
            * 
            * type_binary_op(op, T1, T2) ---->  boolean,
                si op ∈ {eq, neq}
                et (T1 = type_class(A) ou T1 = null)
                et (T2 = type_class(B) ou T2 = null)
                On autorise donc la comparaison de deux objets de classes A et B quelconques.
            */
            return complier.environmentType.BOOLEAN;
        }





        /* en cas d'erreur */
        return null ;
    }

    /*
     * Le prédicat type_instanceof_op indique si on peut appliquer l’opération InstanceOf.
        type_instanceof_op : Type × Type → Type
        type_instanceof_op(T1, T2) , boolean,
        si (T1 = type_class(A) ou T1 = null)
        et T2 = type_class(B)
     */

    public static Type  type_instanceof_op(DecacCompiler compiler , Type type1 , Type type2)
    {
        if(type1.isClassOrNull() && type2.isClass())
        {
            return compiler.environmentType.BOOLEAN;
        }
        return null ;
    }
}
