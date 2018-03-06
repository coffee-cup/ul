package AST;

import java.util.ArrayList;

public class FunctionBody extends ASTNode {
    private ArrayList<Statement> stmts;
    private Block block;

    public FunctionBody(Block block) {
        this.block = block;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }
}
