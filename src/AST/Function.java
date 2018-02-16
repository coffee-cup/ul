package AST;

import java.util.ArrayList;

public class Function extends ASTNode {
    private FunctionDecl decl;
    private FunctionBody body;

    public Function(FunctionDecl decl, FunctionBody body) {
        this.setBody(body);
        this.setDecl(decl);
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

    public void setDecl(FunctionDecl decl) {
        this.decl = decl;
    }

    public FunctionDecl getDecl() {
        return decl;
    }

	public FunctionBody getBody() {
		return body;
	}

	public void setBody(FunctionBody body) {
		this.body = body;
	}
}
