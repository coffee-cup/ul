package AST;

import AST.*;

public class Identifier extends Expression {
    String name;

    public Identifier(String name) {
        this.name = name;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
