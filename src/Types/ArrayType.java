package Types;

import Types.Type;

public class ArrayType extends Type {
    Type arrayOf;
    Integer size;

    public ArrayType(Type arrayOf) {
        this.arrayOf = arrayOf;
        this.size = 0;
    }

    public String toString() {
        return arrayOf.toString() + "[" + size.toString() + "]";
    }
}
