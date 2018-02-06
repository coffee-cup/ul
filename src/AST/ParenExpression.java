package AST;

import AST.*;

public class ParenExpression extends Expression {
    Expression expr;

    public ParenExpression(Expression expr) {
        this.expr = expr;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
