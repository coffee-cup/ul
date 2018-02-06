package AST;

import AST.*;

public class ArrayReference extends Expression {
    Identifier name;
    Expression expr;

    public ArrayReference(Identifier name, Expression expr) {
        this.name = name;
        this.expr = expr;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
