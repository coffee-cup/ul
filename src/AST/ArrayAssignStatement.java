package AST;

import AST.*;

public class ArrayAssignStatement extends Statement {
    Identifier name;
    Expression refExpr;
    Expression assignExpr;

    public ArrayAssignStatement(Identifier name, Expression refExpr, Expression assignExpr) {
        this.name = name;
        this.refExpr = refExpr;
        this.assignExpr = assignExpr;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
