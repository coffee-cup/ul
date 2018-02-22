package Semantics.Exceptions;

import AST.*;
import Types.*;

public class ReturnException extends SemanticException {
    public ReturnException(Type tExpected, Type tReceived, ASTNode node) {
        super("Expected return to be of type " + tExpected.toString() + " Received " + tReceived.toString(), node);
    }
}
