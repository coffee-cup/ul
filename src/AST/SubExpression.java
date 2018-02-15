package AST;

public class SubExpression extends OperatorExpression {
    public SubExpression(Expression e1, Expression e2, int line, int offset) {
        super(e1, e2);
        operatorSymbol = "-";
        this.line = line;
        this.offset = offset;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
