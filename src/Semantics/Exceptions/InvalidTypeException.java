package Semantics.Exceptions;

import AST.*;
import Types.*;

public class InvalidTypeException extends SemanticException {
    public InvalidTypeException(FormalParameter p) {
        super("parameter " + p.getIdent().getName() + " cannot have type " + p.getType().getType().toString(), p.getIdent());
    }
}
