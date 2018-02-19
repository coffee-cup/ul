package AST;

import java.io.PrintStream;

public class PrintVisitor implements Visitor<Void> {
    private PrintStream out;
    private int indentLevel = 0;

    public PrintVisitor(PrintStream out) {
        this.out = out;
    }

    public Void visit(AssignStatement s) {
        s.getName().accept(this);
        out.print("=");
        s.getExpr().accept(this);
        semi();

        return null;
    }

    public Void visit(ArrayAssignStatement s) {
        s.getName().accept(this);
        openSquare();
        s.getRefExpr().accept(this);
        closeSquare();
        out.print("=");
        s.getAssignExpr().accept(this);
        semi();

        return null;
    }

    public Void visit(ArrayReference a) {
        a.getName().accept(this);
        openSquare();
        a.getExpr().accept(this);
        closeSquare();

        return null;
    }

    public Void visit(Block b) {
        for (Statement s : b.getStmts()) {
            printIndent();
            s.accept(this);
            newLine();
        }

        return null;
    }

    public Void visit(BooleanLiteral b) {
        if (b.getValue()) {
            out.print("true");
        } else {
            out.print("false");
        }

        return null;
    }

    public Void visit(CharacterLiteral c) {
        out.print("'");
        out.print(c.getValue());
        out.print("'");

        return null;
    }

    public Void visit(ExpressionStatement e) {
        if (e.getExpr() != null) {
            e.getExpr().accept(this);
        }
        semi();

        return null;
    }

    public Void visit(FloatLiteral f) {
        out.printf("%f", f.getValue());

        return null;
    }

    public Void visit(FormalParameter p) {
        p.getTypeNode().accept(this);
        space();
        p.getIdent().accept(this);

        return null;
    }

    public Void visit(Function f) {
        // Declaration
        f.getDecl().accept(this);

        // Body
        f.getBody().accept(this);

        closeBrace();
        newLine();

        return null;
    }

    public Void visit(FunctionBody f) {
        forwardIndent();

        for (VariableDeclaration v : f.getVars()) {
            printIndent();
            v.accept(this);
            newLine();
        }

        if (f.getVars().size() > 0) {
            newLine();
        }

        int index = 0;
        for (Statement s : f.getStmts()) {
            printIndent();
            s.accept(this);
            index += 1;
            newLine();
        }

        backIndent();

        return null;
    }

    public Void visit(FunctionCall f) {
        f.getName().accept(this);
        openParen();

        int index = 0;
        for (Expression e : f.getParams()) {
            e.accept(this);
            if (index != f.getParams().size() - 1) {
                commaSep();
            }
            index += 1;
        }

        closeParen();

        return null;
    }

    public Void visit(FunctionDecl decl) {
        decl.getTypeNode().accept(this);
        space();
        decl.getIdent().accept(this);
        space();
        openParen();

        // Parameters
        for (int i = 0; i < decl.getParams().size(); i += 1) {
            decl.getParams().get(i).accept(this);

            if (i < decl.getParams().size() - 1) {
                commaSep();
            }
        }

        closeParen();
        newLine();
        openBrace();
        newLine();

        return null;
    }

    public Void visit(Identifier i) {
        out.print(i.getName());

        return null;
    }

    public Void visit(IfStatement i) {
        out.print("if");

        // Expression condition
        space();
        openParen();
        i.getExpr().accept(this);
        closeParen();
        newLine();
        printIndent();
        openBrace();
        newLine();

        // Then block
        forwardIndent();
        i.getThenBlock().accept(this);
        backIndent();
        printIndent();
        closeBrace();

        // Else block
        if (i.getElseBlock() != null) {
            newLine();
            printIndent();
            out.print("else");
            newLine();
            printIndent();
            openBrace();
            newLine();

            forwardIndent();
            i.getElseBlock().accept(this);
            backIndent();
            printIndent();
            closeBrace();
        }

        return null;
    }

    public Void visit(IntegerLiteral i) {
        out.print(i.getValue());

        return null;
    }

    public Void visit(OperatorExpression e) {
        e.e1.accept(this);
        out.print(e.operatorSymbol);
        e.e2.accept(this);

        return null;
    }

    public Void visit(ParenExpression p) {
        openParen();
        p.getExpr().accept(this);
        closeParen();

        return null;
    }

    public Void visit(PrintStatement s) {
        String ps = "print";
        if (s.isNewline()) {
            ps += "ln";
        }

        out.print(ps);
        space();
        s.getExpr().accept(this);
        semi();

        return null;
    }

    public Void visit(Program p) {
        int index = 0;
        for (Function f : p.getFunctions()) {
            if (index != 0) {
                newLine();
            }
            f.accept(this);
            index += 1;
        }

        return null;
    }

    public Void visit(ReturnStatement s) {
        out.print("return");

        if (s.getExpr() != null) {
            space();
            s.getExpr().accept(this);
        }
        semi();

        return null;
    }

    public Void visit(StringLiteral s) {
        out.print('"');
        out.print(s.getValue());
        out.print('"');

        return null;
    }

    public Void visit(TypeNode t) {
        out.print(t.getType().toString());

        return null;
    }

    public Void visit(VariableDeclaration v) {
        v.getTypeNode().accept(this);
        space();
        v.getIdent().accept(this);
        semi();

        return null;
    }

    public Void visit(WhileStatement s) {
        out.print("while");
        space();
        openParen();
        s.getExpr().accept(this);
        closeParen();
        newLine();
        printIndent();
        openBrace();
        newLine();

        // Block
        forwardIndent();
        s.getBlock().accept(this);
        backIndent();
        printIndent();
        closeBrace();

        return null;
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
