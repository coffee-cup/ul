package AST;

public class ParenExpression extends Expression {
    private Expression expr;

    public ParenExpression(Expression expr) {
        this.setExpr(expr);
        this.setPosition(expr);
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
