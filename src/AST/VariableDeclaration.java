package AST;

import AST.*;
import Types.*;

public class VariableDeclaration extends ASTNode {
    TypeNode type;
    Identifier ident;

    public VariableDeclaration(TypeNode type, Identifier ident) {
        this.type = type;
        this.ident = ident;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
