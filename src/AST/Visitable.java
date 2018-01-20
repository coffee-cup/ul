package AST;

import AST.Visitor;

public interface Visitable {
    public void accept(Visitor v);
}
