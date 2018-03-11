package AST;

import java.util.ArrayList;
import Types.Type;

public class FunctionCall extends Expression {
    private Identifier name;
    private ArrayList<Expression> params;

    // When type checking we will tag the function call with
    // the actual declaration that will be called
    private FunctionDecl declCalled;

    public FunctionCall(Identifier name, ArrayList<Expression> params) {
        this.setName(name);
        this.setParams(params);
        this.setPosition(name);
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

    public FunctionDecl getDeclCalled() {
        return declCalled;
    }

    public void setDeclCalled(FunctionDecl declCalled) {
        this.declCalled = declCalled;
    }

    public String getCallString(ArrayList<Type> paramTypes) {
        String s = getName() + "(";

        int i = 0;
        for (Type t: paramTypes) {
            s += t.toString();
            if (i != paramTypes.size() - 1) s += ", ";
            i += 1;
        }
        s += ")";
        return s;
    }
}
