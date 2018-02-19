package Types;

public class CharType extends Type {
    public CharType() {}

    public boolean check(Type t) {
        return (t instanceof CharType);
    }

    public String toString() {
        return "char";
    }
}
