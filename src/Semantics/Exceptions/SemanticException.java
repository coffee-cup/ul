package Semantics.Exceptions;

import AST.ASTNode;

public class SemanticException extends RuntimeException {
    public SemanticException(String message) {
        super(message);
    }

    static String getPositionMessage(ASTNode node) {
        return "    line: " + node.getLine() + " offset: " + node.getOffset();
    }
}
