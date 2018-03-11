package IR.Instructions;

public class IRUnaryOp extends IRInstruction {
    private Temp dest;
    private Temp source;
    private IRUOp operation;

    public IRUnaryOp(Temp dest, Temp source, IRUOp operation) {
        this.dest = dest;
        this.source = source;
        this.operation = operation;
    }

    public Temp getDest() {
        return dest;
    }

    public Temp getSource() {
        return source;
    }

    public IRUOp getOperation() {
        return operation;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
