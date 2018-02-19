package Types;

public class IntegerType extends Type {
    public IntegerType() {}

    public boolean check(Type t) {
        return (t instanceof IntegerType);
    }

    public String toString() {
        return "int";
    }
}
