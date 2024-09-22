lexer grammar DecaLexer;

options {
   language=Java;
   // Tell ANTLR to make the generated lexer class extend the
   // the named class, which is where any supporting code and
   // variables will be placed.
   superClass = AbstractDecaLexer;
}

@members {
   
}

// Deca lexer rules.


EQUALS : '=';
OBRACE : '{';
CBRACE : '}';
SEMI : ';';
COMMA : ',';
OPARENT : '(';
CPARENT : ')';
PRINT : 'print';
PRINTX : 'printx';
PRINTLN : 'println'; 
PRINTLNX : 'printlnx';
WHILE : 'while';
RETURN : 'return';
IF : 'if';
ELSE : 'else';
OR : '||';
AND : '&&';
EQEQ : '==';
NEQ : '!=';
LEQ : '<=';
GEQ : '>=';
GT : '>' ;
LT : '<';
INSTANCEOF : 'instanceof';
PLUS : '+';
MINUS : '-';
TIMES : '*';
SLASH : '/';
PERCENT : '%';
EXCLAM : '!';
READINT : 'readInt';
READFLOAT : 'readFloat';
NEW : 'new';

EOL: '\n' {skip();};


ESPACE : ' ' {skip();};



fragment LETTER : ('a'..'z'
         |'A'..'Z');

fragment DIGIT : '0'..'9';



INT : DIGIT+;
// float

fragment SIGN : ('+'|'-');
fragment EXP : ('E' | 'e') SIGN INT;
fragment DIGITHEX : (DIGIT | 'A'..'F'|'a'..'f');
fragment NUMHEX : DIGITHEX+;
fragment FLOATHEX : ('0x' | '0X') NUMHEX ('.' DIGITHEX*)? ('p' | 'P') SIGN? INT ('f' | 'F')?;


FLOAT
    : (INT '.' INT* EXP? ('f' | 'F')? // Decimal float
      | ('0x' | '0X') NUMHEX ('.' DIGITHEX*)? EXP? ('f' | 'F')? // Hex float
      |  FLOATHEX
      );




DOT : '.';



fragment STRING_CAR : ~('\\' | '"' | '\n');
STRING : '"' (STRING_CAR | '\\"' | '\\\\'  )* '"';
MULTI_LINE_STRING : '"' (STRING_CAR | EOL | '\\"' | '\\\\' | '\\\''  )* '"';


TRUE : 'true' ;
FALSE : 'false';
THIS : 'this';
NULL : 'null';
CLASS : 'class';
EXTENDS : 'extends';
PROTECTED : 'protected';
PUBLIC : 'public';
ASM : 'asm';
INCLUDE : '#include' (' ')* STRING { doInclude(getText()); };





IDENT:(LETTER | '$' | '_')(LETTER | DIGIT | '$' | '_')*;

// Ignore spaces, tabs, newlines and whitespaces
WS  :   ('\t'
        | '\r'
        ) {
              skip(); // avoid producing a token
          }
    ;

COMMENT : ('/*' .*? '*/'
         | '//' .*? EOL)
         { skip(); } ;



