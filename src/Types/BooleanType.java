package Types;

public class BooleanType extends Type {
    public static Type instance;

    public BooleanType() {}

    public static Type getInstance() {
        if (instance == null) {
            instance = new BooleanType();
        }
        return instance;
    }

    public static boolean check(Type t) {
        return (t instanceof BooleanType);
    }

    public String toString() {
        return "boolean";
    }

    public String toIRString() {
        return "Z";
    }
}
