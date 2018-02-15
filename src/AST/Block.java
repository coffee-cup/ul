package AST;

import java.util.ArrayList;

public class Block extends ASTNode {
    private ArrayList<Statement> stmts;

    public Block(ArrayList<Statement> stmts) {
        this.setStmts(stmts);
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

	public ArrayList<Statement> getStmts() {
		return stmts;
	}

	public void setStmts(ArrayList<Statement> stmts) {
		this.stmts = stmts;
	}
}
