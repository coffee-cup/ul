package IR.Instructions;

public class IRLabel extends IRInstruction {
    private int number;

    public IRLabel(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
