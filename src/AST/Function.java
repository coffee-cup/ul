package AST;

import java.util.ArrayList;

public class Function extends ASTNode {
    private TypeNode type;
    private Identifier ident;
    private ArrayList<FormalParameter> params;
    private FunctionBody body;

    public Function(TypeNode type, Identifier ident, ArrayList<FormalParameter> params, FunctionBody body) {
        this.setType(type);
        this.setIdent(ident);
        this.setParams(params);
        this.setBody(body);
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

	public TypeNode getType() {
		return type;
	}

	public void setType(TypeNode type) {
		this.type = type;
	}

	public Identifier getIdent() {
		return ident;
	}

	public void setIdent(Identifier ident) {
		this.ident = ident;
	}

	public ArrayList<FormalParameter> getParams() {
		return params;
	}

	public void setParams(ArrayList<FormalParameter> params) {
		this.params = params;
	}

	public FunctionBody getBody() {
		return body;
	}

	public void setBody(FunctionBody body) {
		this.body = body;
	}
}
