package IR.Instructions;

public class IRArrayReference extends IRInstruction {
    private Temp dest;
    private Temp arrayTemp;
    private Temp refTemp;

    public IRArrayReference(Temp dest, Temp arrayTemp, Temp refTemp) {
        this.dest = dest;
        this.arrayTemp = arrayTemp;
        this.refTemp = refTemp;
    }

    public Temp getDest() {
        return dest;
    }

    public Temp getArrayTemp() {
        return arrayTemp;
    }

    public Temp getRefTemp() {
        return refTemp;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
