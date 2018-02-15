package AST;

import Types.Type;

public class TypeNode extends ASTNode {
    private Type type;

    public TypeNode(Type type) {
        this.type = type;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

    public String toString() {
        return type.toString();
    }
    
    public Type getType() {
    		return this.type;
    }
}
