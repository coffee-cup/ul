package AST;

import AST.*;
import Types.*;


public class TypeNode extends ASTNode {
    Type type;

    public TypeNode(Type type) {
        this.type = type;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

    public String toString() {
        return type.toString();
    }
}
