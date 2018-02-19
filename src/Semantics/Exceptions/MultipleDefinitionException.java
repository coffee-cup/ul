package Semantics.Exceptions;

import AST.*;
import Types.*;

public class MultipleDefinitionException extends SemanticException {
    public MultipleDefinitionException(FunctionDecl prevDecl, FunctionDecl decl) {
        super("function " + decl.toString() + " previously defined on line " + prevDecl.getIdent().getLine(), decl.getIdent());
    }

    public MultipleDefinitionException(FormalParameter p) {
        super("parameter " + p.getIdent().getName() + " already defined", p.getIdent());
    }

    public MultipleDefinitionException(VariableDeclaration v) {
        super("variable " + v.getIdent().getName() + " already defined", v.getIdent());
    }
}
