package AST;

import Types.*;
import AST.*;

public class FormalParameter extends ASTNode {
    Type type;
    Identifier ident;

    public FormalParameter(Type type, Identifier ident) {
        this.type = type;
        this.ident = ident;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
