package IR.Instructions;

public class IRReturn extends IRInstruction {
    private Temp temp;

    public IRReturn() {}

    public IRReturn(Temp temp) {
        this.temp = temp;
    }

    public Temp getTemp() {
        return temp;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
