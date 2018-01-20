package AST;

import AST.*;
import Types.*;
import java.util.ArrayList;

public class FunctionBody extends ASTNode {
    ArrayList<VariableDeclaration> vars;

    public FunctionBody(ArrayList<VariableDeclaration> vars) {
        this.vars = vars;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
