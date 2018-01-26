package AST;

import Types.*;
import AST.*;

public class FormalParameter extends ASTNode {
    TypeNode type;
    Identifier ident;

    public FormalParameter(TypeNode type, Identifier ident) {
        this.type = type;
        this.ident = ident;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
