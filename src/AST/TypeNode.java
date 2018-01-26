package AST;

import AST.*;
import Types.*;


public class TypeNode extends ASTNode {
    Type type;

    public TypeNode(Type type) {
        this.type = type;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
