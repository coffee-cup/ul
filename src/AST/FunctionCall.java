package AST;

import AST.*;
import java.util.ArrayList;

public class FunctionCall extends Expression {
    Identifier name;
    ArrayList<Expression> params;

    public FunctionCall(Identifier name, ArrayList<Expression> params) {
        this.name = name;
        this.params = params;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
