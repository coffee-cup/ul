package AST;

public class FloatLiteral extends Literal {
    private float value;

    public FloatLiteral(float value, int line, int offset) {
        this.value = value;
        this.line = line;
        this.offset = offset;
    }

    public float getValue() {
        return value;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
