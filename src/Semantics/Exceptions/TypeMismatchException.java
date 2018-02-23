package Semantics.Exceptions;

import AST.*;
import Types.*;

public class TypeMismatchException extends SemanticException {
    public TypeMismatchException(Type tExpected, Type tReceived, ASTNode node) {
        this("", tExpected, tReceived, node);
    }

    public TypeMismatchException(String message, Type tExpected, Type tReceived, ASTNode node) {
        super((message.equals("") ? "" : message + " ") + "Expected " + tExpected.toString() + " Received " + tReceived.toString(), node);
    }

    public TypeMismatchException(Type tLeft, Type tRight, OperatorExpression expr) {
        super(tLeft.toString() + " is not compatible with " + tRight.toString() + " in " + expr.getOperatorSymbol() + " expression", expr);
    }

    public TypeMismatchException(String expectedType, Type tReceived, ASTNode node) {
        super("Expected " + expectedType + " Received " + tReceived.toString(), node);
    }
}
