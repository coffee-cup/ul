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

program         : function+
	;

function        : functionDecl functionBody
    ;

functionDecl    : type identifier '(' ')'
    ;

functionBody    : '{' '}'
    ;

identifier      : ID
    ;

type            : TYPE
    ;

/* Lexer */

IF              : 'if'
    ;

TYPE            : 'int'
    ;

ID              : ('a'..'z')+
    ;


/* These two lines match whitespace and comments 
 * and ignore them.
 * You want to leave these as last in the file.  
 * Add new lexical rules above 
 */
WS              : ( '\t' | ' ' | ('\r' | '\n') )+ { $channel = HIDDEN;}
    ;

COMMENT         : '//' ~('\r' | '\n')* ('\r' | '\n') { $channel = HIDDEN;}
    ;
