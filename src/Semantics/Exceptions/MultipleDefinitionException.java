package Semantics.Exceptions;

import AST.*;
import Types.*;

public class MultipleDefinitionException extends SemanticException {
    public MultipleDefinitionException(FunctionDecl prevDecl, FunctionDecl decl) {
        super("Function " + decl.toString() + " previously defined on line " + prevDecl.getIdent().getLine(), decl.getIdent());
    }

    public MultipleDefinitionException(FormalParameter p) {
        super("Parameter " + p.getIdent().getName() + " already defined", p.getIdent());
    }

    public MultipleDefinitionException(VariableDeclaration v) {
        super("Variable " + v.getIdent().getName() + " already defined", v.getIdent());
    }
}
