package IR.Instructions;

public enum IRUOp {
    NEGATE,
    INVERT,
    TOFLOAT;

    @Override
    public String toString() {
        switch(this) {
        case NEGATE: return "-";
        case INVERT: return "!";
        case TOFLOAT: return "2F";
        }
        return "";
    }
}
