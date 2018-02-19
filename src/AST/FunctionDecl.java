package AST;

import java.util.ArrayList;
import Types.Type;

public class FunctionDecl extends ASTNode {
    private TypeNode type;
    private Identifier ident;
    private ArrayList<FormalParameter> params;

    public FunctionDecl(TypeNode type, Identifier ident, ArrayList<FormalParameter> params) {
        this.setTypeNode(type);
        this.setIdent(ident);
        this.setParams(params);
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

    public String toString() {
        String s = type.getType().toString() + " " + ident.getName() + " (";

        int i = 0;
        for (FormalParameter fp: params) {
            s += fp.getType().toString();
            if (i != params.size() - 1) s += ", ";
            i += 1;
        }
        s += ")";
        return s;
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

	public ArrayList<FormalParameter> getParams() {
		return params;
	}

	public void setParams(ArrayList<FormalParameter> params) {
		this.params = params;
	}

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof FunctionDecl) {
            return ((FunctionDecl) obj).toString().equals(this.toString());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
