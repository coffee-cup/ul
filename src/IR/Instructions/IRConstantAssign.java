package IR.Instructions;

import IR.Constants.IRConstant;

public class IRConstantAssign extends IRInstruction {
    private Temp operand;
    private IRConstant constant;

    public IRConstantAssign(Temp operand, IRConstant constant) {
        this.operand = operand;
        this.constant = constant;
    }

    public Temp getOperand() {
        return operand;
    }

    public IRConstant getConstant() {
        return constant;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
