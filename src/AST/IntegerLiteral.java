package AST;

import AST.*;

public class IntegerLiteral extends Literal {
    int value;

    public IntegerLiteral(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
