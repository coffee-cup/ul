package AST;

import AST.*;

public class IfStatement extends Statement {
    // Expression expr;
    Block thenBlock;
    Block elseBlock;

    public IfStatement(Block thenBlock) {
        this.thenBlock = thenBlock;
    }

    public IfStatement(Block thenBlock, Block elseBlock) {
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
