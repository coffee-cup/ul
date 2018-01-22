package AST;

import AST.*;
import java.util.ArrayList;

public class Block extends ASTNode {
    ArrayList<Statement> stmts;

    public Block(ArrayList<Statement> stmts) {
        this.stmts = stmts;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
