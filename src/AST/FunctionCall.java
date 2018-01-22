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

    public void accept(Visitor v) {
        v.visit(this);
    }
}
