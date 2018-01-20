package AST;

import Types.*;
import AST.*;
import java.io.PrintStream;

public class PrintVisitor implements Visitor {
	PrintStream out;

	public PrintVisitor() {
		out = System.out;
	}

	// public void visit(AddExpression e);

	// public void visit(ArrayType a);

	// public void visit(ArrayAssignment s);

	// public void visit(ArrayReference a);

	// public void visit(Block b);

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
        for (VariableDeclaration v : f.vars) {
            v.accept(this);
            newLine();
        }
    }

	// public void visit(FunctionCall f);

	// public void visit(FunctionDeclaration f);

	public void visit(Identifier i) {
		out.print(i.name);
	}

	// public void visit(IdentifierValue v);

	// public void visit(IfStatement i);

	public void visit(IntegerLiteral i) {
		out.print(i.value);
	}

	// public void visit(LessThanExpression e);

	// public void visit(MultExpression e);

	// public void visit(ParenExpression p);

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
