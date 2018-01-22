package AST;

import AST.*;

public class SubExpression extends OperatorExpression {
    public SubExpression(Expression e1, Expression e2) {
        super(e1, e2);
        operatorSymbol = "-";
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
