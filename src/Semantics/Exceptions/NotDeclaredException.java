package Semantics.Exceptions;

import AST.*;
import Types.*;

public class NotDeclaredException extends SemanticException {
    public NotDeclaredException(Identifier i) {
        super("Variable " + i.getName() + " is not declared", i);
    }

    public NotDeclaredException(String s, ASTNode node) {
        super("Function " + s + " is not declared", node);
    }
}
