package IR.Constants;

public abstract class IRConstant {
    public abstract String toString();

    public String toJVMString() {
        return toString();
    }
}
