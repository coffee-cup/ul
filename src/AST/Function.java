package AST;

import Types.*;
import AST.*;
import java.util.ArrayList;

public class Function extends ASTNode {
	Type type;
	Identifier ident;
	ArrayList<FormalParameter> params;
	FunctionBody body;

	public Function(Type type, Identifier ident, ArrayList<FormalParameter> params, FunctionBody body) {
		this.type = type;
		this.ident = ident;
		this.params = params;
		this.body = body;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
