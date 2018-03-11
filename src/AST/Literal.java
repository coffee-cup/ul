package AST;

import Types.Type;

public abstract class Literal extends Expression {
    public abstract Type getType();
}
