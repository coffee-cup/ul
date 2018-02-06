package AST;

import AST.*;
import java.util.ArrayList;

public class Block extends ASTNode {
    ArrayList<Statement> stmts;

    public Block(ArrayList<Statement> stmts) {
        this.stmts = stmts;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
