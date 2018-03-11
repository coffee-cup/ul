package IR.Instructions;

public class IRPrintln extends IRInstruction {
    private Temp temp;

    public IRPrintln(Temp temp) {
        this.temp = temp;
    }

    public Temp getTemp() {
        return temp;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
