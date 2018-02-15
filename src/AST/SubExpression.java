package AST;

public class SubExpression extends OperatorExpression {
    public SubExpression(Expression e1, Expression e2) {
        super(e1, e2);
        operatorSymbol = "-";
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
