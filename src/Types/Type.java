package Types;

import AST.Visitable;
import AST.Visitor;

public abstract class Type implements Visitable {
    abstract public String toString();

    public void accept(Visitor v) {
        v.visit(this);
    }
}
