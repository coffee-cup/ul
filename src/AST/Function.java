package AST;

import Types.*;
import AST.*;
import java.util.ArrayList;

public class Function extends ASTNode {
    TypeNode type;
    Identifier ident;
    ArrayList<FormalParameter> params;
    FunctionBody body;

    public Function(TypeNode type, Identifier ident, ArrayList<FormalParameter> params, FunctionBody body) {
        this.type = type;
        this.ident = ident;
        this.params = params;
        this.body = body;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
