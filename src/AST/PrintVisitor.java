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

	// public void visit(AddExpression e);

	// public void visit(ArrayType a);

	// public void visit(ArrayAssignment s);

	// public void visit(ArrayReference a);

	public void visit(Block b) {
        for (Statement s : b.stmts) {
            if (s != null) {
                printIndent();
                s.accept(this);
            }
            // s.accept(this);
        }
	}

	// public void visit(BooleanLiteral b);

	// public void visit(CharacterLiteral c);

	// public void visit(DoStatement s);

	// public void visit(EqualityExpression e);

	// public void visit(ExpressionStatement e);

	// public void visit(FloatLiteral f);

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

        for (Statement s: f.stmts) {
            newLine();
            printIndent();
            s.accept(this);
        }

        backIndent();
	}

	// public void visit(FunctionCall f);

	// public void visit(FunctionDeclaration f);

	public void visit(Identifier i) {
		out.print(i.name);
	}

	// public void visit(IdentifierValue v);

	public void visit(IfStatement i) {
        out.print("if");
        space();
        openParen();
        closeParen();
        newLine();
        printIndent();
        openBrace();

        forwardIndent();
        i.thenBlock.accept(this);
        if (i.elseBlock != null) {
            i.elseBlock.accept(this);
        }
        backIndent();
        printIndent();
        closeBrace();
	}

	public void visit(IntegerLiteral i) {
		out.print(i.value);
	}

	// publwic void visit(LessThanExpression e);

	// pubrlic void visit(MultExpression e);

	// pfoublic void visit(ParenExpression p);

	// public void visit(PrintLnStatement s);

	// public void visit(PrintStatement s);

	public void visit(Program p) {
		for (Function f : p.functions) {
			f.accept(this);
		}
	}

	// public void visit(ReturnStatement s);

	// public void visit(StringLiteral s);

	// public void visit(SubtractExpression e);

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
