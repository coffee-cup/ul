grammar ulGrammar;

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

program
    : function+
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

statement
options { backtrack = true; }
    : SEMI
    | IF LPARENS expr RPARENS block ELSE block
    | IF LPARENS expr RPARENS block
    | WHILE LPARENS expr RPARENS block
    | PRINT expr SEMI
    | PRINTLN expr SEMI
    | RETURN expr? SEMI
    | identifier LSQUARE expr RSQUARE EQUALS SEMI
    | expr SEMI
    ;

block
    : LCURLY statement* RCURLY
    ;

expr
    : literal
    | identifier LSQUARE expr RSQUARE
    | identifier LPARENS exprList RPARENS
    | identifier
    | LPARENS expr RPARENS
    ;

literal
    : STRINGC
    | INTEGERC
    | FLOATC
    | CHARC
    | TRUE
    | FALSE
    ;

exprList
    : expr exprMore*
    |
    ;

exprMore
    : COMMA expr
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

IF
    : 'if'
    ;

ELSE
    : 'else'
    ;

WHILE
    : 'while'
    ;

PRINT
    : 'print'
    ;

PRINTLN
    : 'println'
    ;

RETURN
    : 'return'
    ;

TYPE
    : ('int' | 'float' | 'char' | 'string' | 'boolean' | 'void')
    ;

ID
    : (LETTER | '_') (LETTER | DIGIT | '_')*
    ;

INTEGERC
    : DIGIT+
    ;

STRINGC
    : '"' (LETTER | DIGIT | '_' | '!' | '.' | ',' | '?' | '-' | ' ')* '"'
    ;

CHARC
    : '\'' (LETTER | DIGIT | '_' | '!' | '.' | '?' | '-' | ' ') '\''
    ;

FLOATC
    : (DIGIT+)'.'(DIGIT+)?
    ;

TRUE
    : 'true'
    ;

FALSE
    : 'false'
    ;

EQUALS
    : '='
    ;

SEMI
    : ';'
    ;

COMMA
    : ','
    ;

LPARENS
    : '('
    ;

RPARENS
    : ')'
    ;

LSQUARE
    : '['
    ;

RSQUARE
    : ']'
    ;

LCURLY
    : '{'
    ;

RCURLY
    : '}'
    ;


/* Whitespace */

WS
    : ( '\t' | ' ' | ('\r' | '\n') )+ { $channel = HIDDEN;}
    ;

COMMENT
    : '//' ~('\r' | '\n')* ('\r' | '\n') { $channel = HIDDEN;}
    ;
