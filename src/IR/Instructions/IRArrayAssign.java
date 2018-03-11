package IR.Instructions;

public class IRArrayAssign extends IRInstruction {
    private Temp dest;
    private Temp refTemp;
    private Temp assignTemp;

    public IRArrayAssign(Temp dest, Temp refTemp, Temp assignTemp) {
        this.dest = dest;
        this.refTemp = refTemp;
        this.assignTemp = assignTemp;
    }

    public Temp getDest() {
        return dest;
    }

    public Temp getRefTemp() {
        return refTemp;
    }

    public Temp getAssignTemp() {
        return assignTemp;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
