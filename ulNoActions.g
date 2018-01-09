grammar ulNoActions;

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
    : compoundType identifier LPARENS RPARENS
    ;

functionBody
    : LCURLY varDecl* RCURLY
    ;

varDecl
    : compoundType identifier SEMI
    ;

identifier
    : ID
    ;

compoundType
    : TYPE
    | TYPE LSQUARE INTEGERC RSQUARE
    ;

literal
    : STRINGC
    | INTEGERC
    | FLOATC
    | CHARC
    | TRUE
    | FALSE
    ;

/* Fragments */

/* Lexer */

IF
    : 'if'
    ;

WHILE
    : 'while'
    ;

TYPE
    : ('int' | 'float' | 'char' | 'string' | 'boolean' | 'void')
    ;

ID
    : ('a'..'z')+
    ;

INTEGERC
    : ('0'..'9')+
    ;

STRINGC
    : '"'.*'"'
    ;

CHARC
    : '\''.'\''
    ;

FLOATC
    : ('0'..'9')+'.'('0'..'9')+
    ;

TRUE
    : 'true'
    ;

FALSE
    : 'false'
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
