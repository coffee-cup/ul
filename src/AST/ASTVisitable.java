package AST;

import AST.ASTVisitor;

public interface ASTVisitable {
    public <T> T accept(ASTVisitor<T> v);
}
