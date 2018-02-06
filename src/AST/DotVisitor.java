package AST;

import Types.*;
import AST.*;
import java.io.PrintStream;

public class DotVisitor implements Visitor<Void> {
    private PrintStream out;
    private int indentLevel = 0;

    public DotVisitor(PrintStream out) {
        this.out = out;
    }

    public Void visit(AssignStatement s) {
        labelNode(s);

        connectNodes(s, s.name);
        connectNodes(s, s.expr);

        s.name.accept(this);
        s.expr.accept(this);

        return null;
    }

    public Void visit(ArrayAssignStatement s) {
        labelNode(s);

        connectNodes(s, s.name);
        connectNodes(s, s.refExpr);
        connectNodes(s, s.assignExpr);

        s.name.accept(this);
        s.refExpr.accept(this);
        s.assignExpr.accept(this);

        return null;
    }

    public Void visit(ArrayReference a) {
        labelNode(a);
        connectNodes(a, a.name);
        connectNodes(a, a.expr);

        a.name.accept(this);
        a.expr.accept(this);

        return null;
    }

    public Void visit(Block b) {
        labelNode(b);

        for (Statement s : b.stmts) {
            connectNodes(b, s);
            s.accept(this);
        }

        return null;
    }

    public Void visit(BooleanLiteral b) {
        labelNode(b, Boolean.toString(b.value));

        return null;
    }

    public Void visit(CharacterLiteral c) {
        labelNode(c, Character.toString(c.value));

        return null;
    }

    public Void visit(ExpressionStatement e) {
        labelNode(e);
        if (e.expr != null) {
            connectNodes(e, e.expr);
            e.expr.accept(this);
        }

        return null;
    }

    public Void visit(FloatLiteral f) {
        labelNode(f, Float.toString(f.value));

        return null;
    }

    public Void visit(FormalParameter p) {
        labelNode(p, p.type.toString() + " " + p.ident.name);

        return null;
    }

    public Void visit(Function f) {
        printIndent();
        out.print("subgraph " + f.ident.name + " ");
        openBrace();
        newLine();
        forwardIndent();

        labelNode(f, f.type.toString() + " " + f.ident.name);

        connectNodes(f, f.params);
        labelNode(f.params, "FormalParameters");
        for (FormalParameter p : f.params) {
            connectNodes(f.params, p);
            p.accept(this);
        }
        connectNodes(f, f.body);
        f.body.accept(this);

        backIndent();
        printIndent();
        closeBrace();
        newLine();

        return null;
    }

    public Void visit(FunctionBody f) {
        labelNode(f);

        connectNodes(f, f.vars);
        labelNode(f.vars, "VariableDeclarations");

        for (VariableDeclaration v : f.vars) {
            connectNodes(f.vars, v);
            v.accept(this);
        }

        connectNodes(f, f.stmts);
        labelNode(f.stmts, "Statements");

        for (Statement s : f.stmts) {
            connectNodes(f.stmts, s);
            s.accept(this);
        }

        return null;
    }

    public Void visit(FunctionCall f) {
        labelNode(f, f.name.name);

        for (Expression e : f.params) {
            connectNodes(f, e);
            e.accept(this);
        }

        return null;
    }

    public Void visit(Identifier i) {
        labelNode(i, i.name);

        return null;
    }

    public Void visit(IfStatement i) {
        labelNode(i);

        connectNodes(i, i.expr);
        i.expr.accept(this);

        connectNodes(i, i.thenBlock);
        i.thenBlock.accept(this);

        if (i.elseBlock != null) {
            connectNodes(i, i.elseBlock);
            i.elseBlock.accept(this);
        }

        return null;
    }

    public Void visit(IntegerLiteral i) {
        labelNode(i, Integer.toString(i.value));

        return null;
    }

    public Void visit(OperatorExpression e) {
        labelNode(e);

        connectNodes(e, e.e1);
        connectNodes(e, e.e2);

        e.e1.accept(this);
        e.e2.accept(this);

        return null;
    }

    public Void visit(ParenExpression p) {
        labelNode(p);
        connectNodes(p, p.expr);
        p.expr.accept(this);

        return null;
    }

    public Void visit(PrintStatement s) {
        labelNode(s);
        connectNodes(s, s.expr);
        s.expr.accept(this);

        return null;
    }

    public Void visit(Program p) {
        out.print("digraph G");
        space();
        openBrace();
        newLine();

        forwardIndent();

        for (Function f : p.functions) {
            f.accept(this);
            newLine();
        }

        backIndent();
        closeBrace();

        return null;
    }

    public Void visit(ReturnStatement s) {
        labelNode(s);

        if (s.expr != null) {
            connectNodes(s, s.expr);
            s.expr.accept(this);
        }

        return null;
    }

    public Void visit(StringLiteral s) {
        labelNode(s, s.value);

        return null;
    }

    public Void visit(TypeNode t) {
        labelNode(t, t.type.toString());

        return null;
    }

    public Void visit(VariableDeclaration v) {
        labelNode(v, v.type.toString() + " " + v.ident.name);

        return null;
    }

    public Void visit(WhileStatement s) {
        labelNode(s);

        connectNodes(s, s.expr);
        connectNodes(s, s.block);

        s.expr.accept(this);
        s.block.accept(this);
  
        return null;
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
