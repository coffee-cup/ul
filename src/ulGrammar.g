grammar ulGrammar;

@header {
    import Types.*;
    import AST.*;
    import java.util.ArrayList;
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
    : d=functionDecl body=functionBody
        {
            f = new Function($d.t, $d.i, $d.params, body);
        }
    ;

functionDecl returns [Identifier i, Type t, ArrayList<FormalParameter> params]
    : ct=compoundType ident=identifier LPARENS fps=formalParameters RPARENS
        {
            $i = ident; $t = ct; $params = fps;
        }
    ;

formalParameters returns [ArrayList<FormalParameter> params]
    @init
    {
        params = new ArrayList<FormalParameter>();
    }
    : ct=compoundType i=identifier (fp=moreFormals {
            params.add(fp);
        })*
        {
            fp = new FormalParameter(ct, i);
            params.add(0, fp);
        }
    |
    ;

moreFormals returns [FormalParameter fp]
    : COMMA ct=compoundType i=identifier
        { fp = new FormalParameter(ct, i); }
    ;

functionBody returns [FunctionBody body]
    @init
    {
        ArrayList<VariableDeclaration> vars = new ArrayList<VariableDeclaration>();
        ArrayList<Statement> stmts = new ArrayList<Statement>();
    }
    : LCURLY (v=varDecl { vars.add(v); })* (s=statement { stmts.add(s); })* RCURLY
        { body = new FunctionBody(vars, stmts); }
    ;

varDecl returns [VariableDeclaration v]
    : ct=compoundType i=identifier SEMI
        { $v = new VariableDeclaration(ct, i); }
    ;

compoundType returns [Type t]
    : a=arrayType { $t = a; }
    | t1=type { $t = t1; }
    ;

arrayType returns [Type t]
    : of=type LSQUARE i=integerLiteral RSQUARE
        { $t = new ArrayType(of, i.getValue()); }
    ;

type returns [Type t]
    : INT       { $t = new IntegerType(); }
    | FLOAT     { $t = new FloatType(); }
    | CHAR      { $t = new CharType(); }
    | STRING    { $t = new StringType(); }
    | BOOLEAN   { $t = new BooleanType(); }
    | VOID      { $t = new VoidType(); }
    ;

statement
returns [Statement s]
options { backtrack = true; }
    : SEMI
    | expr SEMI
    | ifStmt=ifStatement           { $s = ifStmt; }
    | whileStatement
    | printStatement
    | returnStatement
    | assignStatement
    | arrayAssignStatement
    ;

ifStatement
returns [IfStatement ifStmt]
options { backtrack = true; }
    : IF LPARENS expr RPARENS thenBlock=block ELSE elseBlock=block
        { $ifStmt = new IfStatement(thenBlock, elseBlock); }
    | IF LPARENS expr RPARENS thenBlock=block
        { $ifStmt = new IfStatement(thenBlock); }
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

block returns [Block b]
    @init { ArrayList<Statement> stmts = new ArrayList<Statement>(); }
    : LCURLY (stmt=statement { stmts.add(stmt); })* RCURLY
        { $b = new Block(stmts); }
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
