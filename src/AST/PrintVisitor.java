package AST;

import Types.*;
import AST.*;
import java.io.PrintStream;

public class PrintVisitor implements Visitor {
	private PrintStream out;
	private int indentLevel = 0;

	public PrintVisitor() {
		out = System.out;
	}

	public void visit(AddExpression e) {
		out.print("ADD EXPR");
	}

	// public void visit(ArrayType a);

	// public void visit(ArrayAssignment s);

	public void visit(ArrayReference a) {
        a.name.accept(this);
        openSquare();
        a.expr.accept(this);
        closeSquare();
    }

	public void visit(Block b) {
		for (Statement s : b.stmts) {
			if (s != null) {
				printIndent();
				s.accept(this);
			}
			// s.accept(this);
		}
	}

	public void visit(BooleanLiteral b) {
		if (b.value) {
			out.print("true");
		} else {
			out.print("false");
		}
	}

	public void visit(CharacterLiteral c) {
		out.print(c.value);
	}

	// public void visit(DoStatement s);

	public void visit(EqualityExpression e) {
		out.print("EQUIALITY EXPR");
	}

	// public void visit(ExpressionStatement e);

	public void visit(FloatLiteral f) {
		out.print(f.value);
	}

	public void visit(FormalParameter p) {
		p.type.accept(this);
		space();
		p.ident.accept(this);
	}

	public void visit(Function f) {
		f.type.accept(this);
		space();
		f.ident.accept(this);
		space();
		openParen();

		// Parameters
		for (int i = 0; i < f.params.size(); i += 1) {
			f.params.get(i).accept(this);

			if (i < f.params.size() - 1) {
				commaSep();
			}
		}

		closeParen();
		newLine();
		openBrace();

		// Body
		f.body.accept(this);

		closeBrace();
		newLine();
	}

	public void visit(FunctionBody f) {
		forwardIndent();

		for (VariableDeclaration v : f.vars) {
			printIndent();
			v.accept(this);
			newLine();
		}

		int index = 0;
		for (Statement s : f.stmts) {
			if (f.vars.size() > 0 || index > 0)
				newLine();
			printIndent();
			s.accept(this);
			index += 1;
		}

		backIndent();
	}

	public void visit(FunctionCall f) {
        f.name.accept(this);
        openParen();

        int index = 0;
        for (Expression e : f.params) {
            e.accept(this);
            if (index != f.params.size() - 1) {
                commaSep();
            }
            index += 1;
        }

        closeParen();
    }

	// public void visit(FunctionDeclaration f);

	public void visit(Identifier i) {
		out.print(i.name);
	}

	// public void visit(IdentifierValue v);

	public void visit(IfStatement i) {
		out.print("if");

		// Expression condition
		space();
		openParen();
		i.expr.accept(this);
		closeParen();
		newLine();
		printIndent();
		openBrace();

		// Then block
		forwardIndent();
		i.thenBlock.accept(this);
		backIndent();
		printIndent();
		closeBrace();

		// Else block
		if (i.elseBlock != null) {
			printIndent();
			out.print("else");
			newLine();
			printIndent();
			openBrace();

			forwardIndent();
			i.elseBlock.accept(this);
			backIndent();
			printIndent();
			closeBrace();
		}
	}

	public void visit(IntegerLiteral i) {
		out.print(i.value);
	}

	public void visit(LessThanExpression e) {
		out.print("LESS THAN EXPR");
	}

	public void visit(MultExpression e) {
		out.print("MULT EXPR");
	}

	public void visit(ParenExpression p) {
		printIndent();
		openParen();

		p.expr.accept(this);

		printIndent();
		closeParen();
	}

	// public void visit(PrintLnStatement s);

	// public void visit(PrintStatement s);

	public void visit(Program p) {
		for (Function f : p.functions) {
			f.accept(this);
		}
	}

	// public void visit(ReturnStatement s);

	public void visit(StringLiteral s) {
		out.print(s.value);
	}

	public void visit(SubExpression e) {
		out.print("SUB EXPR");
	}

	public void visit(Type t) {
		out.print(t.toString());
	}

	// public void visit(TypeNode t);

	// public void visit(VariableAssignment s);

	public void visit(VariableDeclaration v) {
		v.type.accept(this);
		space();
		v.ident.accept(this);
		semi();
	}

	// public void visit(WhileStatement s);

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
		out.print("{\n");
	}

	private void closeBrace() {
		out.print("}\n");
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
