package AST;

import AST.*;

public abstract class OperatorExpression extends Expression {
    Expression e1;
    Expression e2;
    String operatorSymbol;

    public OperatorExpression(Expression e1, Expression e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
}
