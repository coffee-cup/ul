package AST;

import AST.*;

public class ParenExpression extends Expression {
    Expression expr;

    public ParenExpression(Expression expr) {
        this.expr = expr;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
