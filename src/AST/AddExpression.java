package AST;

public class AddExpression extends OperatorExpression {
    public AddExpression(Expression e1, Expression e2, int line, int offset) {
        super(e1, e2);
        this.setOperatorSymbol("+");
        this.line = line;
        this.offset = offset;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
