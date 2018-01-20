package AST;

import AST.*;

public class Function extends ASTNode {
    String name;

    public Function(String name) {
        this.name = name;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
