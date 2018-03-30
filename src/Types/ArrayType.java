package Types;

import Types.Type;

public class ArrayType extends Type {
    private Type arrayOf;
    private int size;

    public ArrayType(Type arrayOf, int size) {
        this.arrayOf = arrayOf;
        this.size = size;
    }

    public static Type getInstance() {
        throw new RuntimeException("Cannot getInstance of ArrayType");
    }

    public static boolean check(Type t) {
        return (t instanceof ArrayType);
    }

    public static String toTypeString() {
        return "array";
    }

    public int getSize() {
        return size;
    }

    public Type getArrayOfType() {
        return arrayOf;
    }

    public String toString() {
        return arrayOf.toString() + "[" + size + "]";
    }

    public String toIRString() {
        return "A" + arrayOf.toIRString();
    }

    public String toJVMString() {
        return "a";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (obj instanceof Type && ArrayType.check((Type) obj)) {
            return (((ArrayType)obj).getSize() == this.getSize());
        }

        return false;
    }
}
