package IR.Instructions;

public enum IRUOp {
    INVERT,
    TOFLOAT;

    @Override
    public String toString() {
        switch(this) {
        case INVERT: return "!";
        case TOFLOAT: return "2F";
        }
        return "";
    }
}

