package AST;

import AST.*;

public class LessThanExpression extends OperatorExpression {
    public LessThanExpression(Expression e1, Expression e2) {
        super(e1, e2);
        operatorSymbol = "<";
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
