package IR.Constants;

public class IRBooleanConstant extends IRConstant {
    private boolean value;

    public IRBooleanConstant(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public String toString() {
        return value ? "TRUE" : "FALSE";
    }

    @Override
    public String toJVMString() {
        return Integer.toString(value ? 1 : 0);
    }
}
