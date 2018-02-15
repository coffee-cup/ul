package Types;

public abstract class Type {
    abstract public String toString();

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
        		return false;
        }

        return true;
    }
}
