package AST;

import Types.*;
import AST.*;

public class Function extends ASTNode {
    Type type;
    Identifier ident;

    public Function(Type type, Identifier ident) {
        this.type = type;
        this.ident = ident;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
