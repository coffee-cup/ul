package AST;

import AST.*;

public class WhileStatement extends Statement {
    Expression expr;
    Block block;

    public WhileStatement(Expression expr, Block block) {
        this.expr = expr;
        this.block = block;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
