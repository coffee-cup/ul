package AST;

public abstract class OperatorExpression extends Expression {
    protected Expression e1;
    protected Expression e2;
    protected String operatorSymbol;

    public OperatorExpression(Expression e1, Expression e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
}
