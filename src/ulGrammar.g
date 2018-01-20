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
    : function+ EOF
        {
            p = new Program();
        }
	;

function
    : functionDecl functionBody
    ;

functionDecl
    : compoundType identifier LPARENS formalParameters RPARENS
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

compoundType
    : TYPE LSQUARE INTEGERC RSQUARE
    | TYPE
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
intLiteral    : INTEGERC ;
floatLiteral  : FLOATC ;
charLiteral   : CHARC ;
boolLiteral   : TRUE | FALSE ;

literal
    : stringLiteral
    | intLiteral
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

identifier
    : ID
    ;

/* Fragments */

fragment LETTER
    : ('a'..'z' | 'A'..'Z')
    ;

fragment DIGIT
    : ('0'..'9')
    ;

/* Lexer */

TYPE : ('int' | 'float' | 'char' | 'string' | 'boolean' | 'void') ;

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
