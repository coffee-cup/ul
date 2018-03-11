package IR.Instructions;

public class IRBinaryOp extends IRInstruction {
    private Temp dest;
    private Temp leftOperand;
    private Temp rightOperand;
    private IRBOp operation;

    public IRBinaryOp(Temp dest, Temp leftOperand, Temp rightOperand, IRBOp operation) {
        this.dest = dest;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.operation = operation;
    }

    public Temp getDest() {
        return dest;
    }

    public Temp getLeftOperand() {
        return leftOperand;
    }

    public Temp getRightOperand() {
        return rightOperand;
    }

    public IRBOp getOperation() {
        return operation;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
