package Semantics.Exceptions;

import AST.ASTNode;

public class SemanticException extends RuntimeException {
    public SemanticException(String message) {
        super("\nError: " + message);
    }

    public SemanticException(String message, ASTNode node) {
        super("\nError:" + getPositionMessage(node) + " " + message + "\n");
    }

    static String getPositionMessage(ASTNode node) {
        return node.getLine() + ":" + node.getOffset();
    }
}
