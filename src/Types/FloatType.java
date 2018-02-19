package Types;

public class FloatType extends Type {
    public FloatType() {}

    public boolean check(Type t) {
        return (t instanceof FloatType);
    }

    public String toString() {
        return "float";
    }
}
