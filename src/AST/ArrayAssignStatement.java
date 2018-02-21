package AST;

public class ArrayAssignStatement extends Statement {
    private Identifier name;
    private Expression refExpr;
    private Expression assignExpr;

    public ArrayAssignStatement(Identifier name, Expression refExpr, Expression assignExpr) {
        this.setName(name);
        this.setRefExpr(refExpr);
        this.setAssignExpr(assignExpr);
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

    public Identifier getName() {
        return name;
    }

    public Identifier getName(Identifier name) {
        return this.name;
    }

    public void setName(Identifier name) {
        this.name = name;
    }

    public Expression getRefExpr() {
        return refExpr;
    }

    public void setRefExpr(Expression refExpr) {
        this.refExpr = refExpr;
    }

    public Expression getAssignExpr() {
        return assignExpr;
    }

    public void setAssignExpr(Expression assignExpr) {
        this.assignExpr = assignExpr;
    }
}
