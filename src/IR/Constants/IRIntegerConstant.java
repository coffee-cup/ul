package IR.Constants;

public class IRIntegerConstant extends IRConstant {
    private int value;

    public IRIntegerConstant(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return Integer.toString(value);
    }
}
