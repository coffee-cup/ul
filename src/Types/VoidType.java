package Types;

public class VoidType extends Type {
    public VoidType() {}

    public boolean check(Type t) {
        return (t instanceof VoidType);
    }

    public String toString() {
        return "void";
    }
}
