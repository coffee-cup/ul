package AST;

import AST.*;

public class AddExpression extends OperatorExpression {
    public AddExpression(Expression e1, Expression e2) {
        super(e1, e2);
        operatorSymbol = "+";
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
