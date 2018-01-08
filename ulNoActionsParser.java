// $ANTLR 3.0.1 ulNoActions.g 2018-01-08 00:49:48

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class ulNoActionsParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ID", "TYPE", "IF", "WS", "COMMENT", "'('", "')'", "'{'", "'}'"
    };
    public static final int COMMENT=8;
    public static final int ID=4;
    public static final int WS=7;
    public static final int EOF=-1;
    public static final int TYPE=5;
    public static final int IF=6;

        public ulNoActionsParser(TokenStream input) {
            super(input);
        }
        

    public String[] getTokenNames() { return tokenNames; }
    public String getGrammarFileName() { return "ulNoActions.g"; }


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



    // $ANTLR start program
    // ulNoActions.g:25:1: program : ( function )+ ;
    public final void program() throws RecognitionException {
        try {
            // ulNoActions.g:25:17: ( ( function )+ )
            // ulNoActions.g:25:19: ( function )+
            {
            // ulNoActions.g:25:19: ( function )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==TYPE) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ulNoActions.g:25:19: function
            	    {
            	    pushFollow(FOLLOW_function_in_program30);
            	    function();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }

        }

            catch (RecognitionException ex) {
                reportError(ex);
                throw ex;
            }
        finally {
        }
        return ;
    }
    // $ANTLR end program


    // $ANTLR start function
    // ulNoActions.g:28:1: function : functionDecl functionBody ;
    public final void function() throws RecognitionException {
        try {
            // ulNoActions.g:28:17: ( functionDecl functionBody )
            // ulNoActions.g:28:19: functionDecl functionBody
            {
            pushFollow(FOLLOW_functionDecl_in_function48);
            functionDecl();
            _fsp--;

            pushFollow(FOLLOW_functionBody_in_function50);
            functionBody();
            _fsp--;


            }

        }

            catch (RecognitionException ex) {
                reportError(ex);
                throw ex;
            }
        finally {
        }
        return ;
    }
    // $ANTLR end function


    // $ANTLR start functionDecl
    // ulNoActions.g:31:1: functionDecl : type identifier '(' ')' ;
    public final void functionDecl() throws RecognitionException {
        try {
            // ulNoActions.g:31:17: ( type identifier '(' ')' )
            // ulNoActions.g:31:19: type identifier '(' ')'
            {
            pushFollow(FOLLOW_type_in_functionDecl66);
            type();
            _fsp--;

            pushFollow(FOLLOW_identifier_in_functionDecl68);
            identifier();
            _fsp--;

            match(input,9,FOLLOW_9_in_functionDecl70); 
            match(input,10,FOLLOW_10_in_functionDecl72); 

            }

        }

            catch (RecognitionException ex) {
                reportError(ex);
                throw ex;
            }
        finally {
        }
        return ;
    }
    // $ANTLR end functionDecl


    // $ANTLR start functionBody
    // ulNoActions.g:34:1: functionBody : '{' '}' ;
    public final void functionBody() throws RecognitionException {
        try {
            // ulNoActions.g:34:17: ( '{' '}' )
            // ulNoActions.g:34:19: '{' '}'
            {
            match(input,11,FOLLOW_11_in_functionBody88); 
            match(input,12,FOLLOW_12_in_functionBody90); 

            }

        }

            catch (RecognitionException ex) {
                reportError(ex);
                throw ex;
            }
        finally {
        }
        return ;
    }
    // $ANTLR end functionBody


    // $ANTLR start identifier
    // ulNoActions.g:37:1: identifier : ID ;
    public final void identifier() throws RecognitionException {
        try {
            // ulNoActions.g:37:17: ( ID )
            // ulNoActions.g:37:19: ID
            {
            match(input,ID,FOLLOW_ID_in_identifier108); 

            }

        }

            catch (RecognitionException ex) {
                reportError(ex);
                throw ex;
            }
        finally {
        }
        return ;
    }
    // $ANTLR end identifier


    // $ANTLR start type
    // ulNoActions.g:40:1: type : TYPE ;
    public final void type() throws RecognitionException {
        try {
            // ulNoActions.g:40:17: ( TYPE )
            // ulNoActions.g:40:19: TYPE
            {
            match(input,TYPE,FOLLOW_TYPE_in_type132); 

            }

        }

            catch (RecognitionException ex) {
                reportError(ex);
                throw ex;
            }
        finally {
        }
        return ;
    }
    // $ANTLR end type


 

    public static final BitSet FOLLOW_function_in_program30 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_functionDecl_in_function48 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_functionBody_in_function50 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_functionDecl66 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_identifier_in_functionDecl68 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_9_in_functionDecl70 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_functionDecl72 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_functionBody88 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_functionBody90 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_identifier108 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TYPE_in_type132 = new BitSet(new long[]{0x0000000000000002L});

}