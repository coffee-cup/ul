package AST;

import Types.CharType;
import Types.Type;

public class CharacterLiteral extends Literal {
    private char value;

    public CharacterLiteral(char value, int line, int offset) {
        this.value = value;
        this.line = line;
        this.offset = offset;
    }

    public char getValue() {
        return value;
    }

    public Type getType() {
        return CharType.getInstance();
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
