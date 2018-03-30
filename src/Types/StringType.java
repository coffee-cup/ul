package Types;

public class StringType extends Type {
    public static Type instance;

    public StringType() {}

    public static Type getInstance() {
        if (instance == null) {
            instance = new StringType();
        }
        return instance;
    }

    public static boolean check(Type t) {
        return (t instanceof StringType);
    }

    public String toString() {
        return "string";
    }

    public String toIRString() {
        return "U";
    }

    public String toJVMString() {
        return "a";
    }
}
