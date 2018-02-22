package AST;

public class ReturnStatement extends Statement {
    private Expression expr;

    public ReturnStatement(Expression expr, int line, int offset) {
        this.setExpr(expr);
        this.line = line;
        this.offset = offset;
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
}
