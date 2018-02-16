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
    @init { p = new Program(); }
    : (f=function { p.getFunctions().add(f); })+ EOF
    ;

function returns [Function f]
    : decl=functionDecl body=functionBody
        { f = new Function(decl, body); }
    ;

functionDecl returns [FunctionDecl decl]
    : ct=compoundType ident=identifier LPARENS fps=formalParameters RPARENS
        { $decl = new FunctionDecl(ct, ident, fps); }
    ;

formalParameters returns [ArrayList<FormalParameter> params]
    @init
    { params = new ArrayList<FormalParameter>(); }
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

compoundType returns [TypeNode t]
    : a=arrayType { $t = a; }
    | t1=type { $t = t1; }
    ;

arrayType returns [TypeNode t]
    : of=type LSQUARE i=integerLiteral RSQUARE
        { $t = new TypeNode(new ArrayType(of.getType(), i.getValue()), of.getLine(), of.getOffset()); }
    ;

type returns [TypeNode t]
    : INT       { $t = new TypeNode(new IntegerType(), $INT.line, $INT.pos); }
    | FLOAT     { $t = new TypeNode(new FloatType(), $FLOAT.line, $FLOAT.pos); }
    | CHAR      { $t = new TypeNode(new CharType(), $CHAR.line, $CHAR.pos); }
    | STRING    { $t = new TypeNode(new StringType(), $STRING.line, $STRING.pos); }
    | BOOLEAN   { $t = new TypeNode(new BooleanType(), $BOOLEAN.line, $BOOLEAN.pos); }
    | VOID      { $t = new TypeNode(new VoidType(), $VOID.line, $VOID.pos); }
    ;

statement
returns [Statement s]
options { backtrack = true; }
    : SEMI                          { $s = new ExpressionStatement(null); }
    | e=expr SEMI                   { $s = new ExpressionStatement(e); }
    | ifStmt=ifStatement            { $s = ifStmt; }
    | whileStmt=whileStatement      { $s = whileStmt; }
    | printStmt=printStatement      { $s = printStmt; }
    | returnStmt=returnStatement    { $s = returnStmt; }
    | assignStmt=assignStatement    { $s = assignStmt; }
    | aaStmt=arrayAssignStatement   { $s = aaStmt; }
    ;

ifStatement
returns [IfStatement ifStmt]
options { backtrack = true; }
    : IF LPARENS e=expr RPARENS thenBlock=block ELSE elseBlock=block
        { $ifStmt = new IfStatement(e, thenBlock, elseBlock); }
    | IF LPARENS e=expr RPARENS thenBlock=block
        { $ifStmt = new IfStatement(e, thenBlock); }
    ;

whileStatement returns [WhileStatement s]
    : WHILE LPARENS e=expr RPARENS b=block
        { s = new WhileStatement(e, b); }
    ;

printStatement returns [PrintStatement s]
    : PRINT e=expr SEMI { s = new PrintStatement(e); }
    | PRINTLN e=expr SEMI { s = new PrintStatement(e, true); }
    ;

returnStatement returns [ReturnStatement s]
    : RETURN e=expr? SEMI
        { s = new ReturnStatement(e); }
    ;

assignStatement returns [AssignStatement s]
    : i=identifier EQUALS e=expr SEMI
        { s = new AssignStatement(i, e); }
    ;

arrayAssignStatement returns [ArrayAssignStatement s]
    : i=identifier LSQUARE e1=expr RSQUARE EQUALS e2=expr SEMI
        { s = new ArrayAssignStatement(i, e1, e2); }
    ;

block returns [Block b]
    @init { ArrayList<Statement> stmts = new ArrayList<Statement>(); }
    : LCURLY (stmt=statement { stmts.add(stmt); })* RCURLY
        { $b = new Block(stmts); }
    ;

expr returns [Expression e]
    : e1=doubleEqExpr { $e = e1; }
    ;

doubleEqExpr returns [Expression e]
    @init { Expression it = null; }
    @after { $e = it; }
    : e1=lessThanExpr { it = e1; }
        (DOUBEQOP e2=lessThanExpr { it = new EqualityExpression(it, e2, $DOUBEQOP.line, $DOUBEQOP.pos); })*
    ;

lessThanExpr returns [Expression e]
    @init { Expression it = null; }
    @after { $e = it; }
    : e1=addSubExpr { it = e1; }
        (LESSOP e2=addSubExpr { it = new LessThanExpression(it, e2, $LESSOP.line, $LESSOP.pos); } )*
    ;

addSubExpr returns [Expression e]
    @init { Expression it = null; }
    @after { $e = it; }
    : e1=multExpr { it = e1; }
        ( (ADDOP e2=multExpr { it = new AddExpression(it, e2, $ADDOP.line, $ADDOP.pos); })
        | (SUBOP e2=multExpr { it = new SubExpression(it, e2, $SUBOP.line, $SUBOP.pos); })
    )*
    ;

multExpr returns [Expression e]
    @init { Expression it = null; }
    @after { $e = it; }
    : e1=atom { it = e1; }
        (MULTOP e2=atom { it = new MultExpression(it, e2, $MULTOP.line, $MULTOP.pos); } )*
    ;

atom returns [Expression e]
    : id=identifier             { $e = id; }
    | lit=literal               { $e = lit; }
    | ref = arrayReference      { $e = ref; }
    | call=functionCall         { $e = call; }
    | LPARENS pe=expr RPARENS   { $e = new ParenExpression(pe); }
    ;

arrayReference returns [ArrayReference a]
    : i=identifier LSQUARE e=expr RSQUARE
        { a = new ArrayReference(i, e); }
    ;

functionCall returns [Expression e]
    : i=identifier LPARENS params=exprList RPARENS
        { $e = new FunctionCall(i, params); }
    ;

stringLiteral returns [StringLiteral s]
    : STRINGC { $s = new StringLiteral($STRINGC.text.substring(1, $STRINGC.text.length() - 1), $STRINGC.line, $STRINGC.pos); }
    ;

integerLiteral returns [IntegerLiteral i]
    : INTEGERC { $i = new IntegerLiteral(Integer.parseInt($INTEGERC.text), $INTEGERC.line, $INTEGERC.pos); }
    ;

floatLiteral returns [FloatLiteral f]
    : FLOATC { $f = new FloatLiteral(Float.parseFloat($FLOATC.text), $FLOATC.line, $FLOATC.pos); }
    ;

charLiteral returns [CharacterLiteral c]
    : CHARC { $c = new CharacterLiteral($CHARC.text.charAt(1), $CHARC.line, $CHARC.pos); }
    ;

boolLiteral returns [BooleanLiteral b]
    : (TRUE  { $b = new BooleanLiteral(true, $TRUE.line, $TRUE.pos); })
    | (FALSE { $b = new BooleanLiteral(false, $FALSE.line, $FALSE.pos); })
    ;

literal returns [Literal l]
    : s=stringLiteral       { $l = s; }
    | i=integerLiteral      { $l = i; }
    | f=floatLiteral        { $l = f; }
    | c=charLiteral         { $l = c; }
    | b=boolLiteral         { $l = b; }
    ;

exprList returns [ArrayList<Expression> params]
    @init { params = new ArrayList<Expression>(); }
    : e=expr { params.add(0, e); }
        (e=exprMore { params.add(e); })*
    |
    ;

exprMore returns [Expression e]
    : COMMA e1=expr { $e = e1; }
    ;

op
    : MULTOP
    | SUBOP
    | ADDOP
    | LESSOP
    | DOUBEQOP
    ;

identifier returns [Identifier i]
    : ID { $i = new Identifier($ID.text, $ID.line, $ID.pos); }
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
