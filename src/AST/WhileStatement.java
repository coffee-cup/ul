package AST;

public class WhileStatement extends Statement {
    private Expression expr;
    private Block block;

    public WhileStatement(Expression expr, Block block) {
        this.setExpr(expr);
        this.setBlock(block);
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

	public Expression getExpr() {
		return expr;
	}

	public void setExpr(Expression expr) {
		this.expr = expr;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}
}
