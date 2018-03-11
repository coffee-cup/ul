package IR.Instructions;

public class IRGoto extends IRInstruction {
    private IRLabel jump;

    public IRGoto(IRLabel jump) {
        this.jump = jump;
    }

    public IRLabel getJump() {
        return jump;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
