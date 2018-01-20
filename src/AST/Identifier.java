package AST;

import AST.*;

public class Identifier extends ASTNode {
    String name;

    public Identifier(String name) {
        this.name = name;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
