package Types;

public class FloatType extends Type {
    public static Type instance;

    public FloatType() {}

    public static Type getInstance() {
        if (instance == null) {
            instance = new FloatType();
        }
        return instance;
    }

    public static boolean check(Type t) {
        return (t instanceof FloatType);
    }

    public String toString() {
        return "float";
    }
}
