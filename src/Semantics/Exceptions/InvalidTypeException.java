package Semantics.Exceptions;

import AST.*;
import Types.*;

public class InvalidTypeException extends SemanticException {
    public InvalidTypeException(FormalParameter p) {
        super("Parameter " + p.getIdent().getName() + " cannot have type " + p.getType().toString(), p.getTypeNode());
    }

    public InvalidTypeException(VariableDeclaration p) {
        super("Variable " + p.getIdent().getName() + " cannot have type " + p.getType().toString(), p.getTypeNode());
    }

    public InvalidTypeException(Type t, String forExpr, ASTNode node) {
        super("Type " + t.toString() + " is invalid for " + forExpr, node);
    }

    public InvalidTypeException(ArrayType tArr, ASTNode node) {
        super("Array can not be of type " + tArr.getArrayOfType().toString(), node);
    }
}
