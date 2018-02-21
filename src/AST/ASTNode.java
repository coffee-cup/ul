package AST;

public abstract class ASTNode implements Visitable {
    protected int line = -1;
    protected int offset = -1;

    public int getLine() {
        return this.line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setPosition(ASTNode node) {
        this.setLine(node.getLine());
        this.setOffset(node.getOffset());
    }
}
