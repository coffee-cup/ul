package IR.Instructions;

public enum IRBOp {
    ADD,
    SUB,
    MULT,
    DIV,
    LESSTHAN,
    DOUBEQ;

    @Override
    public String toString() {
        switch(this) {
        case ADD: return "+";
        case SUB: return "-";
        case MULT: return "*";
        case DIV: return "/";
        case LESSTHAN: return "<";
        case DOUBEQ: return "==";
        }
        return "";
    }

    public boolean hasJVMInstr() {
        switch(this) {
        case ADD: return true;
        case SUB: return true;
        case MULT: return true;
        case DIV: return true;
        }
        return false;
    }

    public String toJVMInstr() {
        switch(this) {
        case ADD: return "add";
        case SUB: return "sub";
        case MULT: return "mul";
        case DIV: return "div";
        }
        return "";
    }
}
