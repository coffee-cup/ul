package Semantics.Exceptions;

import AST.*;
import Types.*;

public class TypeMismatchException extends SemanticException {
    public TypeMismatchException(Type t1, Type t2, ASTNode node) {
        super("expected " + t1.toString() + " to be type " + t2.toString(), node);
    }
}
