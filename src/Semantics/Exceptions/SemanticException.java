package Semantics.Exceptions;

import AST.ASTNode;

public class SemanticException extends RuntimeException {
    public SemanticException(String message) {
        super("error: " + message);
    }

    public SemanticException(String message, ASTNode node) {
        super("error: " + message + "\n" + getPositionMessage(node));
    }

    static String getPositionMessage(ASTNode node) {
        return "    line: " + node.getLine() + " offset: " + node.getOffset();
    }
}
