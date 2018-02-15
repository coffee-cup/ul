package AST;

public class BooleanLiteral extends Literal {
    private boolean value;

    public BooleanLiteral(boolean value, int line, int offset) {
        this.value = value;
        this.line = line;
        this.offset = offset;
    }

    public boolean getValue() {
        return value;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
