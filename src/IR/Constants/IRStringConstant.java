package IR.Constants;

public class IRStringConstant extends IRConstant {
    private String value;

    public IRStringConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return value;
    }
}
