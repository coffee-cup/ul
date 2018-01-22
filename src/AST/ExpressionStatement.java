package AST;

import AST.*;

public class ExpressionStatement extends Statement {
    Expression expr;

    public ExpressionStatement(Expression expr) {
        this.expr = expr;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
