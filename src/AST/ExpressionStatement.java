package AST;

import AST.*;

public class ExpressionStatement extends Statement {
    Expression expr;

    public ExpressionStatement(Expression expr) {
        this.expr = expr;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
