package AST;

import AST.*;

public class PrintStatement extends Statement {
    Expression expr;
    boolean newline = false;

    public PrintStatement(Expression expr) {
        this.expr = expr;
    }

    public PrintStatement(Expression expr, boolean newline) {
        this.expr = expr;
        this.newline = newline;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
