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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof TypeNode) {
            return (((TypeNode)obj).getType().equals(this.getType()));
        }
        if (obj instanceof Type) {
            return (((Type)obj).equals(this.getType()));
        }
        return false;
    }
}
