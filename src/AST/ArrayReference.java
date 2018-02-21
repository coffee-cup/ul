package AST;

public class ArrayReference extends Expression {
    private Identifier name;
    private Expression expr;

    public ArrayReference(Identifier name, Expression expr) {
        this.setName(name);
        this.setExpr(expr);
        this.setPosition(name);
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

    public Identifier getName() {
        return name;
    }

    public void setName(Identifier name) {
        this.name = name;
    }

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
    }
}
