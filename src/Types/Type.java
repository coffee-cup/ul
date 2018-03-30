package Types;

public abstract class Type {
    abstract public String toString();
    abstract public String toIRString();
    abstract public String toJVMString();

    public boolean isSubtype(Type t) {
        return false;
    }

    public static Type greaterType(Type t1, Type t2) {
        if (t1.isSubtype(t2)) {
            return t2;
        }
        return t1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        return (getClass() == obj.getClass());
    }
}
