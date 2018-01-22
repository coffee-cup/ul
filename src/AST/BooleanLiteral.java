package AST;

import AST.*;

public class BooleanLiteral extends Literal {
    boolean value;

    public BooleanLiteral(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
