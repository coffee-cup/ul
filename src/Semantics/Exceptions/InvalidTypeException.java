package Semantics.Exceptions;

import AST.*;
import Types.*;

public class InvalidTypeException extends SemanticException {
    public InvalidTypeException(FormalParameter p) {
        super("parameter " + p.getIdent().getName() + " cannot have type " + p.getType().toString(), p.getIdent());
    }

    public InvalidTypeException(VariableDeclaration p) {
        super("variable " + p.getIdent().getName() + " cannot have type " + p.getType().toString(), p.getIdent());
    }
}
