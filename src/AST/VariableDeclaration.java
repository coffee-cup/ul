package AST;

import AST.*;
import Types.*;

public class VariableDeclaration extends ASTNode {
    Type type;
    Identifier ident;

    public VariableDeclaration(Type type, Identifier ident) {
        this.type = type;
        this.ident = ident;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
