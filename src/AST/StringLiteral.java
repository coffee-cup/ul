package AST;

import AST.*;

public class StringLiteral extends Literal {
    String value;

    public StringLiteral(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
