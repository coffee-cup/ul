package IR.Constants;

public class IRCharacterConstant extends IRConstant {
    private char value;

    public IRCharacterConstant(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    public String toString() {
        return "'" + Character.toString(value) + "'";
    }

    @Override
    public String toJVMString() {
        int i = (int) value;
        return Integer.toString(i);
    }
}
