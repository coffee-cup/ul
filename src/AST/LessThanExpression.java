package AST;

import AST.*;

public class LessThanExpression extends OperatorExpression {
    public LessThanExpression(Expression e1, Expression e2) {
        super(e1, e2);
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
