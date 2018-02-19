package AST;

import Types.Type;

public class FormalParameter extends ASTNode {
    private TypeNode type;
    private Identifier ident;

    public FormalParameter(TypeNode type, Identifier ident) {
        this.setTypeNode(type);
        this.setIdent(ident);
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

    public Type getType() {
        return getTypeNode().getType();
    }

    public TypeNode getTypeNode() {
        return type;
    }

	public void setTypeNode(TypeNode type) {
		this.type = type;
	}

	public Identifier getIdent() {
		return ident;
	}

	public void setIdent(Identifier ident) {
		this.ident = ident;
	}
}
