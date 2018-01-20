package AST;

import AST.*;

public class Program extends ASTNode {
	String filename;

	public Program() {
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
