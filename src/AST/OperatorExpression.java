package AST;

public abstract class OperatorExpression extends Expression {
    private Expression e1;
    private Expression e2;
    private String operatorSymbol;

    public OperatorExpression(Expression e1, Expression e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public Expression getLeftExpr() {
        return e1;
    }

    public Expression getRightExpr() {
        return e2;
    }

    public String getOperatorSymbol() {
        return operatorSymbol;
    }

    public void setOperatorSymbol(String operatorSymbol) {
        this.operatorSymbol = operatorSymbol;
    }

    public String toString() {
        return operatorSymbol + " expression";
    }
}
