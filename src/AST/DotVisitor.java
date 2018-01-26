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
		labelNode(s);

		connectNodes(s, s.name);
		connectNodes(s, s.expr);

		s.name.accept(this);
		s.expr.accept(this);
	}

	public void visit(ArrayAssignStatement s) {
	}

	public void visit(ArrayReference a) {
	}

	public void visit(Block b) {
		labelNode(b);

		for (Statement s : b.stmts) {
			connectNodes(b, s);
			s.accept(this);
		}
	}

	public void visit(BooleanLiteral b) {
		labelNode(b, Boolean.toString(b.value));
	}

	public void visit(CharacterLiteral c) {
		labelNode(c, Character.toString(c.value));
	}

	public void visit(ExpressionStatement e) {
		labelNode(e);
		if (e.expr != null) {
			connectNodes(e, e.expr);
			e.expr.accept(this);
		}
	}

	public void visit(FloatLiteral f) {
		labelNode(f, Float.toString(f.value));
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
		labelNode(f.params, "Parameters");
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
	}

	public void visit(FunctionBody f) {
		labelNode(f);

		connectNodes(f, f.vars);
		labelNode(f.vars, "Variable Declarations");

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
	}

	public void visit(FunctionCall f) {
		labelNode(f, f.name.name);

		for (Expression e : f.params) {
			connectNodes(f, e);
			e.accept(this);
		}
	}

	public void visit(Identifier i) {
		labelNode(i, i.name);
	}

	public void visit(IfStatement i) {
		labelNode(i);

		connectNodes(i, i.expr);
		i.expr.accept(this);

		connectNodes(i, i.thenBlock);
		i.thenBlock.accept(this);

		if (i.elseBlock != null) {
			connectNodes(i, i.elseBlock);
			i.elseBlock.accept(this);
		}
	}

	public void visit(IntegerLiteral i) {
		labelNode(i, Integer.toString(i.value));
	}

	public void visit(OperatorExpression e) {
		labelNode(e);

		connectNodes(e, e.e1);
		connectNodes(e, e.e2);

		e.e1.accept(this);
		e.e2.accept(this);
	}

	public void visit(ParenExpression p) {
		labelNode(p);
		connectNodes(p, p.expr);
		p.expr.accept(this);
	}

	public void visit(PrintStatement s) {
		labelNode(s);
		connectNodes(s, s.expr);
		s.expr.accept(this);
	}

	public void visit(Program p) {
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
	}

	public void visit(ReturnStatement s) {
		labelNode(s);

		if (s.expr != null) {
			connectNodes(s, s.expr);
			s.expr.accept(this);
		}
	}

	public void visit(StringLiteral s) {
		labelNode(s, s.value);
	}

	public void visit(TypeNode t) {
		labelNode(t, t.type.toString());
	}

	public void visit(VariableDeclaration v) {
		labelNode(v, v.type.toString() + " " + v.ident.name);
	}

	public void visit(WhileStatement s) {
		labelNode(s);

		connectNodes(s, s.expr);
		connectNodes(s, s.block);

		s.expr.accept(this);
		s.block.accept(this);
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
