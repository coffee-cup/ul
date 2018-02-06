package AST;

import AST.Visitor;

public interface Visitable {
    public <T> T accept(Visitor<T> v);
}
