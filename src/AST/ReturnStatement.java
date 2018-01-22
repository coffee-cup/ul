package AST;

import AST.*;

public class ReturnStatement extends Statement {
    Expression expr;

    public ReturnStatement(Expression expr) {
        this.expr = expr;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
