package Types;

public class CharType extends Type {
    public static Type instance;

    public CharType() {}

    public static Type getInstance() {
        if (instance == null) {
            instance = new CharType();
        }
        return instance;
    }

    public static boolean check(Type t) {
        return (t instanceof CharType);
    }

    public String toString() {
        return "char";
    }

    public String toIRString() {
        return "C";
    }
}
