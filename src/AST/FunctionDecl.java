package AST;

import java.util.ArrayList;

public class FunctionDecl extends ASTNode {
    private TypeNode type;
    private Identifier ident;
    private ArrayList<FormalParameter> params;

    public FunctionDecl(TypeNode type, Identifier ident, ArrayList<FormalParameter> params) {
        this.setType(type);
        this.setIdent(ident);
        this.setParams(params);
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
}
