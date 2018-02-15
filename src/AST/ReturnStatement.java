package AST;

public class ReturnStatement extends Statement {
    private Expression expr;

    public ReturnStatement(Expression expr) {
        this.setExpr(expr);
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
