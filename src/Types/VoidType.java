package Types;

public class VoidType extends Type {
    public static Type instance;

    public VoidType() {}

    public static Type getInstance() {
        if (instance == null) {
            instance = new VoidType();
        }
        return instance;
    }

    public static boolean check(Type t) {
        return (t instanceof VoidType);
    }

    public String toString() {
        return "void";
    }
}
