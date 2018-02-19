package Semantics.Exceptions;

import AST.*;
import Types.*;

public class TypeMismatchException extends SemanticException {
    public TypeMismatchException(AssignStatement s, Type t1, Type t2) {
        super("cannot assign " + t1.toString() + " " + s.getName().toString() + " to " + t2.toString(), s.getName());
    }
}
