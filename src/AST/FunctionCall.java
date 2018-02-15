package AST;

import java.util.ArrayList;

public class FunctionCall extends Expression {
    private Identifier name;
    private ArrayList<Expression> params;

    public FunctionCall(Identifier name, ArrayList<Expression> params) {
        this.setName(name);
        this.setParams(params);
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

	public Identifier getName() {
		return name;
	}

	public void setName(Identifier name) {
		this.name = name;
	}

	public ArrayList<Expression> getParams() {
		return params;
	}

	public void setParams(ArrayList<Expression> params) {
		this.params = params;
	}
}
