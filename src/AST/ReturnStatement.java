package AST;

import AST.*;

public class ReturnStatement extends Statement {
    Expression expr;

    public ReturnStatement(Expression expr) {
        this.expr = expr;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
