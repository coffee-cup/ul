package AST;

import java.io.PrintStream;

public class DotVisitor implements Visitor<Void> {
    private PrintStream out;
    private int indentLevel = 0;

    public DotVisitor(PrintStream out) {
        this.out = out;
    }

    public Void visit(AddExpression e) {
        visitOperatorExpression(e);

        return null;
    }

    public Void visit(AssignStatement s) {
        labelNode(s);

        connectNodes(s, s.getName());
        connectNodes(s, s.getExpr());

        s.getName().accept(this);
        s.getExpr().accept(this);

        return null;
    }

    public Void visit(ArrayAssignStatement s) {
        labelNode(s);

        connectNodes(s, s.getName());
        connectNodes(s, s.getRefExpr());
        connectNodes(s, s.getAssignExpr());

        s.getName().accept(this);
        s.getRefExpr().accept(this);
        s.getAssignExpr().accept(this);

        return null;
    }

    public Void visit(ArrayReference a) {
        labelNode(a);
        connectNodes(a, a.getName());
        connectNodes(a, a.getExpr());

        a.getName().accept(this);
        a.getExpr().accept(this);

        return null;
    }

    public Void visit(Block b) {
        labelNode(b);

        for (Statement s : b.getStmts()) {
            connectNodes(b, s);
            s.accept(this);
        }

        return null;
    }

    public Void visit(BooleanLiteral b) {
        labelNode(b, Boolean.toString(b.getValue()));

        return null;
    }

    public Void visit(CharacterLiteral c) {
        labelNode(c, Character.toString(c.getValue()));

        return null;
    }

    public Void visit(EqualityExpression e) {
        visitOperatorExpression(e);

        return null;
    }

    public Void visit(ExpressionStatement e) {
        labelNode(e);
        if (e.getExpr() != null) {
            connectNodes(e, e.getExpr());
            e.getExpr().accept(this);
        }

        return null;
    }

    public Void visit(FloatLiteral f) {
        labelNode(f, Float.toString(f.getValue()));

        return null;
    }

    public Void visit(FormalParameter p) {
        labelNode(p, p.getType().toString() + " " + p.getIdent().getName());

        return null;
    }

    public Void visit(Function f) {
        labelNode(f);

        connectNodes(f, f.getDecl());
        f.getDecl().accept(this);

        connectNodes(f, f.getBody());
        f.getBody().accept(this);

        backIndent();
        printIndent();
        closeBrace();
        newLine();

        return null;
    }

    public Void visit(FunctionBody f) {
        labelNode(f);

        connectNodes(f, f.getVars());
        labelNode(f.getVars(), "VariableDeclarations");

        for (VariableDeclaration v : f.getVars()) {
            connectNodes(f.getVars(), v);
            v.accept(this);
        }

        connectNodes(f, f.getStmts());
        labelNode(f.getStmts(), "Statements");

        for (Statement s : f.getStmts()) {
            connectNodes(f.getStmts(), s);
            s.accept(this);
        }

        return null;
    }

    public Void visit(FunctionCall f) {
        labelNode(f, f.getName().getName());

        for (Expression e : f.getParams()) {
            connectNodes(f, e);
            e.accept(this);
        }

        return null;
    }

    public Void visit(FunctionDecl decl) {
        labelNode(decl, "FunctionDecl");

        printIndent();
        out.print("subgraph " + decl.getIdent().getName() + " ");
        openBrace();
        newLine();
        forwardIndent();

        labelNode(decl, decl.getType().toString() + " " + decl.getIdent().getName());

        connectNodes(decl, decl.getParams());
        labelNode(decl.getParams(), "FormalParameters");
        for (FormalParameter p : decl.getParams()) {
            connectNodes(decl.getParams(), p);
            p.accept(this);
        }

        return null;
    }

    public Void visit(Identifier i) {
        labelNode(i, i.getName());

        return null;
    }

    public Void visit(IfStatement i) {
        labelNode(i);

        connectNodes(i, i.getExpr());
        i.getExpr().accept(this);

        connectNodes(i, i.getThenBlock());
        i.getThenBlock().accept(this);

        if (i.getElseBlock() != null) {
            connectNodes(i, i.getElseBlock());
            i.getElseBlock().accept(this);
        }

        return null;
    }

    public Void visit(IntegerLiteral i) {
        labelNode(i, Integer.toString(i.getValue()));

        return null;
    }

    public Void visit(LessThanExpression e) {
        visitOperatorExpression(e);

        return null;
    }

    public Void visit(MultExpression e) {
        visitOperatorExpression(e);

        return null;
    }

    public Void visit(ParenExpression p) {
        labelNode(p);
        connectNodes(p, p.getExpr());
        p.getExpr().accept(this);

        return null;
    }

    public Void visit(PrintStatement s) {
        labelNode(s);
        connectNodes(s, s.getExpr());
        s.getExpr().accept(this);

        return null;
    }

    public Void visit(Program p) {
        out.print("digraph G");
        space();
        openBrace();
        newLine();

        forwardIndent();

        labelNode(p);

        for (Function f : p.getFunctions()) {
            connectNodes(p, f);
            f.accept(this);
            newLine();
        }

        backIndent();
        closeBrace();

        return null;
    }

    public Void visit(ReturnStatement s) {
        labelNode(s);

        if (s.getExpr() != null) {
            connectNodes(s, s.getExpr());
            s.getExpr().accept(this);
        }

        return null;
    }

    public Void visit(StringLiteral s) {
        labelNode(s, s.getValue());

        return null;
    }

    public Void visit(SubExpression e) {
        visitOperatorExpression(e);

        return null;
    }

    public Void visit(TypeNode t) {
        labelNode(t, t.getType().toString());

        return null;
    }

    public Void visit(VariableDeclaration v) {
        labelNode(v, v.getType().toString() + " " + v.getIdent().getName());

        return null;
    }

    public Void visit(WhileStatement s) {
        labelNode(s);

        connectNodes(s, s.getExpr());
        connectNodes(s, s.getBlock());

        s.getExpr().accept(this);
        s.getBlock().accept(this);

        return null;
    }

    public void visitOperatorExpression(OperatorExpression e) {
        labelNode(e);

        connectNodes(e, e.getLeftExpr());
        connectNodes(e, e.getRightExpr());

        e.getLeftExpr().accept(this);
        e.getRightExpr().accept(this);
    }

    private void connectNodes(Object fromNode, Object toNode) {
        String fromId = getNodeId(fromNode);
        String toId = getNodeId(toNode);

        printIndent();
        out.print(fromId + "->" + toId);
        semi();
        newLine();
    }

    private void labelNode(Object node) {
        labelNode(node, "");
    }

    private void labelNode(Object node, String info) {
        String className = node.getClass().getSimpleName();
        String id = getNodeId(node);

        String name = className;
        if (!info.equals("")) {
            name += " " + info;
        }

        if (className.equals("ArrayList")) {
            name = info;
        }
        printIndent();
        out.print(id + "[label=\"" + name + "\"];");
        newLine();
    }

    private String getNodeId(Object node) {
        String name = node.getClass().getSimpleName();
        String id = name + "_" + System.identityHashCode(node);
        return id;
    }

    private void forwardIndent() {
        indentLevel += 1;
    }

    private void backIndent() {
        indentLevel -= 1;
        if (indentLevel < 0)
            indentLevel = 0;
    }

    private void printIndent() {
        for (int i = 0; i < indentLevel; i += 1) {
            out.print("    ");
        }
    }

    private void openBrace() {
        out.print("{");
    }

    private void closeBrace() {
        out.print("}");
    }

    private void openParen() {
        out.print("(");
    }

    private void closeParen() {
        out.print(")");
    }

    private void openSquare() {
        out.print("[");
    }

    private void closeSquare() {
        out.print("]");
    }

    private void newLine() {
        out.println("");
    }

    private void space() {
        out.print(" ");
    }

    private void commaSep() {
        out.print(", ");
    }

    private void semi() {
        out.print(";");
    }
}
