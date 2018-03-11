package AST;

import Types.IntegerType;
import Types.Type;

public class IntegerLiteral extends Literal {
    private int value;

    public IntegerLiteral(int value, int line, int offset) {
        this.value = value;
        this.line = line;
        this.offset = offset;
    }

    public int getValue() {
        return value;
    }

    public Type getType() {
        return IntegerType.getInstance();
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
