package Types;

import Types.Type;

public class ArrayType extends Type {
    Type arrayOf;
    int size;

    public ArrayType(Type arrayOf, int size) {
        this.arrayOf = arrayOf;
        this.size = size;
    }

    public Type getInstance() {
        throw new RuntimeException("Cannot getInstance of ArrayType");
    }

    public boolean check(Type t) {
        return (t instanceof ArrayType);
    }

    public String toString() {
        return arrayOf.toString() + "[" + size + "]";
    }
}
