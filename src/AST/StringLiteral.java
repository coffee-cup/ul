package AST;

import Types.StringType;
import Types.Type;

public class StringLiteral extends Literal {
    private String value;

    public StringLiteral(String value, int line, int offset) {
        this.value = value;
        this.line = line;
        this.offset = offset;
    }

    public String getValue() {
        return value;
    }

    public Type getType() {
        return StringType.getInstance();
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
