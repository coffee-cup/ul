package Types;

public class BooleanType extends Type {
    public BooleanType() {}

    public boolean check(Type t) {
        return (t instanceof BooleanType);
    }

    public String toString() {
        return "boolean";
    }
}
