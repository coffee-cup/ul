package IR.Instructions;

public class IRPrint extends IRInstruction {
    private Temp temp;

    public IRPrint(Temp temp) {
        this.temp = temp;
    }

    public Temp getTemp() {
        return temp;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
