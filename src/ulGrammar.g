grammar ulGrammar;

@header {
    import Types.*;
    import AST.*;
}

@members {
  protected void mismatch (IntStream input, int ttype, BitSet follow)
    throws RecognitionException {
    throw new MismatchedTokenException(ttype, input);
  }

  public void recoverFromMismatchedSet (IntStream input,
                                        RecognitionException e,
                                        BitSet follow)
          throws RecognitionException {
            reportError(e);
            throw e;
  }
}

@rulecatch {
    catch (RecognitionException ex) {
        reportError(ex);
        throw ex;
    }
}

/* Parser */

program returns [Program p]
    @init
    {
        p = new Program();
    }
    : (f=function { p.functions.add(f); })+ EOF
	;

function returns [Function f]
    : d=functionDecl functionBody
        {
            f = new Function(d.t, d.i);
        }
    ;

functionDecl returns [Identifier i, Type t]
    : ct=compoundType ident=identifier LPARENS formalParameters RPARENS
        {
            $i = ident; $t = ct;
        }
    ;

formalParameters
    : compoundType identifier moreFormals*
    |
    ;

moreFormals
    : COMMA compoundType identifier
    ;

functionBody
    : LCURLY varDecl* statement* RCURLY
    ;

varDecl
    : compoundType identifier SEMI
    ;

compoundType returns [Type t]
    : a=arrayType { $t = a; }
    | t1=type { $t = t1; }
    ;

arrayType returns [Type t]
    : of=type LSQUARE i=integerLiteral RSQUARE
        {
            $t = new ArrayType(of, i.getValue());
        }
    ;

type returns [Type t]
    : INT       { $t = new IntegerType(); }
    | FLOAT     { $t = new FloatType(); }
    | CHAR      { $t = new CharType(); }
    | STRING    { $t = new StringType(); }
    | BOOLEAN   { $t = new BooleanType(); }
    | VOID      { $t = new VoidType(); }
    ;

statement options { backtrack = true; }
    : SEMI
    | expr SEMI
    | ifStatement
    | whileStatement
    | printStatement
    | returnStatement
    | assignStatement
    | arrayAssignStatement
    ;

ifStatement options { backtrack = true; }
    : IF LPARENS expr RPARENS block ELSE block
    | IF LPARENS expr RPARENS block
    ;

whileStatement
    : WHILE LPARENS expr RPARENS block
    ;

printStatement
    : PRINT expr SEMI
    | PRINTLN expr SEMI
    ;

returnStatement
    : RETURN expr? SEMI
    ;

assignStatement
    : identifier EQUALS expr SEMI
    ;

arrayAssignStatement
    : identifier LSQUARE expr RSQUARE EQUALS expr SEMI
    ;

block
    : LCURLY statement* RCURLY
    ;

expr
    : doubleEqExpr
    ;

doubleEqExpr
    : lessThanExpr (DOUBEQOP lessThanExpr)*
    ;

lessThanExpr
    : addSubExpr (LESSOP addSubExpr)*
    ;

addSubExpr
    : multExpr ((ADDOP | SUBOP) multExpr)*
    ;

multExpr
    : atom (MULTOP atom)*
    ;

atom
    : identifier
    | literal
    | arrayReference
    | functionCall
    | LPARENS expr RPARENS
    ;

arrayReference
    : identifier LSQUARE expr RSQUARE
    ;

functionCall
    : identifier LPARENS exprList RPARENS
    ;

stringLiteral : STRINGC ;
integerLiteral returns [IntegerLiteral i]
    : INTEGERC { i = new IntegerLiteral(Integer.parseInt($INTEGERC.text)); } ;
floatLiteral  : FLOATC ;
charLiteral   : CHARC ;
boolLiteral   : TRUE | FALSE ;

literal
    : stringLiteral
    | integerLiteral
    | floatLiteral
    | charLiteral
    | boolLiteral
    ;

exprList
    : expr exprMore*
    |
    ;

exprMore
    : COMMA expr
    ;

op
    : MULTOP
    | SUBOP
    | ADDOP
    | LESSOP
    | DOUBEQOP
    ;

identifier returns [Identifier i]
    : ID { i = new Identifier($ID.text); }
    ;

/* Fragments */

fragment LETTER
    : ('a'..'z' | 'A'..'Z')
    ;

fragment DIGIT
    : ('0'..'9')
    ;

/* Lexer */

INT         : 'int' ;
FLOAT       : 'float' ;
CHAR        : 'char' ;
STRING      : 'string' ;
BOOLEAN     : 'boolean' ;
VOID        : 'void' ;

IF          : 'if' ;
ELSE        : 'else' ;
WHILE       : 'while' ;
PRINT       : 'print' ;
PRINTLN     : 'println' ;
RETURN      : 'return' ;

INTEGERC    : ('0' | ('1'..'9'('0'..'9')*)) ;
STRINGC     : '"' (LETTER | DIGIT | '_' | '!' | '.' | ',' | '?' | '-' | ' ' | ';')* '"' ;
CHARC       : '\'' (LETTER | DIGIT | '_' | '!' | '.' | ',' | '?' | '-' | ' ' | ';') '\'' ;
FLOATC      : ('0' | ('1'..'9'('0'..'9')*))'.'(DIGIT+)? ;

TRUE        : 'true' ;
FALSE       : 'false' ;

MULTOP      : '*'  ;
SUBOP       : '-'  ;
ADDOP       : '+'  ;
LESSOP      : '<'  ;
DOUBEQOP    : '==' ;

EQUALS      : '=' ;
SEMI        : ';' ;
COMMA       : ',' ;

LPARENS     : '(' ;
RPARENS     : ')' ;
LSQUARE     : '[' ;
RSQUARE     : ']' ;
LCURLY      : '{' ;
RCURLY      : '}' ;

ID : (LETTER | '_') (LETTER | DIGIT | '_')* ;

/* Whitespace */

WS
    : ( '\t' | ' ' | ('\r' | '\n') )+ { $channel = HIDDEN; skip(); }
    ;

COMMENT
    : '//' ~('\r' | '\n')* ('\r' | '\n') { $channel = HIDDEN; skip(); }
    ;
