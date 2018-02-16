package Types;

public abstract class Type {
    abstract public String toString();

    // public boolean is(Class c) {
    //     return (this instanceof c);
    // }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (getClass() != obj.getClass()) {
            return false;
        }

        return true;
    }
}
