package Semantics.Exceptions;

import AST.*;
import Types.*;

public class NotDeclaredException extends SemanticException {
    public NotDeclaredException(Identifier i) {
        super("variable " + i.getName() + " is not declared", i);
    }

    public NotDeclaredException(String s, ASTNode node) {
        super("function " + s + " is not declared", node);
    }
}
