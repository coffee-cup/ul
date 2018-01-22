package AST;

import AST.*;

public class WhileStatement extends Statement {
    Expression expr;
    Block block;

    public WhileStatement(Expression expr, Block block) {
        this.expr = expr;
        this.block = block;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
