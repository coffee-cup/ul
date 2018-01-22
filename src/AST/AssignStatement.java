package AST;

import AST.*;

public class AssignStatement extends Statement {
    Identifier name;
    Expression expr;

    public AssignStatement(Identifier name, Expression expr) {
        this.name = name;
        this.expr = expr;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
