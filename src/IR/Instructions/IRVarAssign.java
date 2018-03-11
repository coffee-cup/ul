package IR.Instructions;

public class IRVarAssign extends IRInstruction {
    private Temp leftOperand;
    private Temp rightOperand;

    public IRVarAssign(Temp leftOperand, Temp rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public Temp getLeftOperand() {
        return leftOperand;
    }

    public Temp getRightOperand() {
        return rightOperand;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
