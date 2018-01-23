package AST;

import Types.*;
import AST.*;
import java.io.PrintStream;

public class DotVisitor implements Visitor {
	private PrintStream out;
	private int indentLevel = 0;

	public DotVisitor(PrintStream out) {
        this.out = out;
	}

    public void visit(AssignStatement s) {
    }

	public void visit(ArrayAssignStatement s) {
    }

	public void visit(ArrayReference a) {
    }

	public void visit(Block b) {
	}

	public void visit(BooleanLiteral b) {
	}

	public void visit(CharacterLiteral c) {
	}

	public void visit(ExpressionStatement e) {
    }

	public void visit(FloatLiteral f) {
	}

	public void visit(FormalParameter p) {
        labelNode(p, p.type.toString() + " " + p.ident.name);
	}

	public void visit(Function f) {
        printIndent();
        out.print("subgraph " + f.ident.name + " ");
        openBrace();
        newLine();
        forwardIndent();

        labelNode(f, f.type.toString() + " " + f.ident.name);

        connectNodes(f, f.params);
        labelNode(f.params, "Params");
        for (FormalParameter p: f.params) {
            connectNodes(f.params, p);
            p.accept(this);
        }
        connectNodes(f, f.body);
        f.body.accept(this);

        backIndent();
        printIndent();
        closeBrace();
        newLine();
	}

	public void visit(FunctionBody f) {
        labelNode(f, "FunctionBody");

        connectNodes(f, f.vars);
        labelNode(f.vars, "Var Decls");

        connectNodes(f, f.stmts);
        labelNode(f.stmts, "Stmts");
	}

	public void visit(FunctionCall f) {
    }

	public void visit(Identifier i) {
	}

	// public void visit(IdentifierValue v);

	public void visit(IfStatement i) {
	}

	public void visit(IntegerLiteral i) {
	}

    public void visit(OperatorExpression e) {
    }

	public void visit(ParenExpression p) {
	}

	public void visit(PrintStatement s) {
    }

	public void visit(Program p) {
        out.print("digraph G");
        space();
        openBrace();
        newLine();

        forwardIndent();

        for (Function f: p.functions) {
            f.accept(this);
            newLine();
        }

        backIndent();
        closeBrace();
	}

	public void visit(ReturnStatement s) {
    }

	public void visit(StringLiteral s) {
	}

	public void visit(Type t) {
        labelNode(t, t.toString());
	}

	public void visit(VariableDeclaration v) {
	}

	public void visit(WhileStatement s) {
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
        String name = node.getClass().getSimpleName();
        labelNode(node, name);
    }

    private void labelNode(Object node, String name) {
        String id = getNodeId(node);

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

