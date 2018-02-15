package AST;

public class MultExpression extends OperatorExpression {
    public MultExpression(Expression e1, Expression e2) {
        super(e1, e2);
        operatorSymbol = "*";
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
