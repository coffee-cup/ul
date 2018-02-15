package AST;

public class PrintStatement extends Statement {
    private Expression expr;
    private boolean newline = false;

    public PrintStatement(Expression expr) {
        this.setExpr(expr);
    }

    public PrintStatement(Expression expr, boolean newline) {
        this.setExpr(expr);
        this.setNewline(newline);
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

	public boolean isNewline() {
		return newline;
	}

	public void setNewline(boolean newline) {
		this.newline = newline;
	}
}
