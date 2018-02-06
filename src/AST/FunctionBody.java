package AST;

import AST.*;
import Types.*;
import java.util.ArrayList;

public class FunctionBody extends ASTNode {
    ArrayList<VariableDeclaration> vars;
    ArrayList<Statement> stmts;

    public FunctionBody(
        ArrayList<VariableDeclaration> vars,
        ArrayList<Statement> stmts) {
        this.vars = vars;
        this.stmts = stmts;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
