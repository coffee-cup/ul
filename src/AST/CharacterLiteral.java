package AST;

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

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
