package IR.Instructions;

public class Label extends IRInstruction {
    private int number;

    public Label(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
