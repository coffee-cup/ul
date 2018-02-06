package AST;

import AST.*;

public class IfStatement extends Statement {
    Expression expr;
    Block thenBlock;
    Block elseBlock;

    public IfStatement(Expression expr, Block thenBlock) {
        this.expr = expr;
        this.thenBlock = thenBlock;
    }

    public IfStatement(Expression expr, Block thenBlock, Block elseBlock) {
        this.expr = expr;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
