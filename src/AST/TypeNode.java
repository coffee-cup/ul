package AST;

import Types.Type;

public class TypeNode extends ASTNode {
    private Type type;

    public TypeNode(Type type, int line, int offset) {
        this.type = type;
        this.line = line;
        this.offset = offset;
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
