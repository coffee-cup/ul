package AST;

public class VariableDeclaration extends ASTNode {
    private TypeNode type;
    private Identifier ident;

    public VariableDeclaration(TypeNode type, Identifier ident) {
        this.setType(type);
        this.setIdent(ident);
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
}
