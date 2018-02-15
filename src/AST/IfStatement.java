package AST;

public class IfStatement extends Statement {
    private Expression expr;
    private Block thenBlock;
    private Block elseBlock;

    public IfStatement(Expression expr, Block thenBlock) {
        this.setExpr(expr);
        this.setThenBlock(thenBlock);
    }

    public IfStatement(Expression expr, Block thenBlock, Block elseBlock) {
        this.setExpr(expr);
        this.setThenBlock(thenBlock);
        this.setElseBlock(elseBlock);
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

	public Block getThenBlock() {
		return thenBlock;
	}

	public void setThenBlock(Block thenBlock) {
		this.thenBlock = thenBlock;
	}

	public Block getElseBlock() {
		return elseBlock;
	}

	public void setElseBlock(Block elseBlock) {
		this.elseBlock = elseBlock;
	}
}
