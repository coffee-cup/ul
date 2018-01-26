package AST;

import Types.*;
import AST.*;
import java.io.PrintStream;

public class PrintVisitor implements Visitor {
	private PrintStream out;
	private int indentLevel = 0;

	public PrintVisitor(PrintStream out) {
        this.out = out;
	}

    public void visit(AssignStatement s) {
        s.name.accept(this);
        out.print("=");
        s.expr.accept(this);
        semi();
    }

	public void visit(ArrayAssignStatement s) {
        s.name.accept(this);
        openSquare();
        s.refExpr.accept(this);
        closeSquare();
        out.print("=");
        s.assignExpr.accept(this);
        semi();
    }

	public void visit(ArrayReference a) {
        a.name.accept(this);
        openSquare();
        a.expr.accept(this);
        closeSquare();
    }

	public void visit(Block b) {
		for (Statement s : b.stmts) {
			printIndent();
			s.accept(this);
            newLine();
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
        out.print("'");
		out.print(c.value);
        out.print("'");
	}

	public void visit(ExpressionStatement e) {
        if (e.expr != null) {
            e.expr.accept(this);
        }
        semi();
    }

	public void visit(FloatLiteral f) {
        out.printf("%f", f.value);
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
        newLine();

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

        if (f.vars.size() > 0) {
            newLine();
        }

		int index = 0;
		for (Statement s : f.stmts) {
			printIndent();
			s.accept(this);
			index += 1;
            newLine();
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
        newLine();

		// Then block
		forwardIndent();
		i.thenBlock.accept(this);
		backIndent();
		printIndent();
		closeBrace();

		// Else block
		if (i.elseBlock != null) {
            newLine();
			printIndent();
			out.print("else");
			newLine();
			printIndent();
			openBrace();
            newLine();

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

    public void visit(OperatorExpression e) {
        e.e1.accept(this);
        out.print(e.operatorSymbol);
        e.e2.accept(this);
    }

	public void visit(ParenExpression p) {
		openParen();
		p.expr.accept(this);
		closeParen();
	}

	public void visit(PrintStatement s) {
        String ps = "print";
        if (s.newline) {
            ps += "ln";
        }

        out.print(ps);
        space();
        s.expr.accept(this);
        semi();
    }

	public void visit(Program p) {
        int index = 0;
		for (Function f : p.functions) {
            if (index != 0) {
                newLine();
            }
			f.accept(this);
            index += 1;
		}
	}

	public void visit(ReturnStatement s) {
        out.print("return");

        if (s.expr != null) {
            space();
            s.expr.accept(this);
        }
        semi();
    }

	public void visit(StringLiteral s) {
        out.print('"');
		out.print(s.value);
        out.print('"');
	}

	public void visit(TypeNode t) {
		out.print(t.type.toString());
	}

	public void visit(VariableDeclaration v) {
		v.type.accept(this);
		space();
		v.ident.accept(this);
		semi();
	}

	public void visit(WhileStatement s) {
        out.print("while");
        space();
        openParen();
        s.expr.accept(this);
        closeParen();
		newLine();
		printIndent();
		openBrace();
        newLine();

		// Block
		forwardIndent();
		s.block.accept(this);
		backIndent();
		printIndent();
		closeBrace();
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
