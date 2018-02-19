package Types;

public class StringType extends Type {
    public StringType() {}

    public boolean check(Type t) {
        return (t instanceof StringType);
    }

    public String toString() {
        return "string";
    }
}
