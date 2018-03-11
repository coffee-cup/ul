package IR.Instructions;

public enum IRBOp {
    ADD,
    SUB,
    MULT,
    DIV,
    REM,
    LESSTHAN,
    LESSTHANEQ,
    DOUBEQ,
    NOTEQ,
    GREATERTHANEQ,
    GREATERTHAN;

    @Override
    public String toString() {
        switch(this) {
        case ADD: return "+";
        case SUB: return "-";
        case MULT: return "*";
        case DIV: return "/";
        case REM: return "rem";
        case LESSTHAN: return "<";
        case LESSTHANEQ: return "<=";
        case DOUBEQ: return "==";
        case NOTEQ: return "!=";
        case GREATERTHANEQ: return ">=";
        case GREATERTHAN: return ">";
        }
        return "";
    }
}
