package IR.Instructions;

public class IRIfStatement extends IRInstruction {
    private Temp cond;
    private IRLabel jump;

    public IRIfStatement(Temp cond, IRLabel jump) {
        this.cond = cond;
        this.jump = jump;
    }

    public Temp getCond() {
        return cond;
    }

    public IRLabel getJump() {
        return jump;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
