package AST;

import java.util.ArrayList;
import AST.*;

public class Program extends ASTNode {
    public ArrayList<Function> functions;
	String filename;

	public Program() {
        functions = new ArrayList<Function>();
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
