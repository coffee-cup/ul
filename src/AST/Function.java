package AST;

import Types.*;
import AST.*;
import java.util.ArrayList;

public class Function extends ASTNode {
    Type type;
    Identifier ident;
    ArrayList<FormalParameter> params;

    public Function(Type type, Identifier ident, ArrayList<FormalParameter> params) {
        this.type = type;
        this.ident = ident;
        this.params = params;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
