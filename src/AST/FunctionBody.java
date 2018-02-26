package AST;

import java.util.ArrayList;

public class FunctionBody extends ASTNode {
    private ArrayList<VariableDeclaration> vars;
    private ArrayList<Statement> stmts;
    private Block block;

    public FunctionBody(Block block) {
        this.block = block;
    }

    // public FunctionBody(ArrayList<Statement> stmts) {
        // this.setVars(vars);
        // this.setStmts(stmts);
    // }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

	public ArrayList<VariableDeclaration> getVars() {
		return vars;
	}

	public void setVars(ArrayList<VariableDeclaration> vars) {
		this.vars = vars;
	}

	public ArrayList<Statement> getStmts() {
		return stmts;
	}

	public void setStmts(ArrayList<Statement> stmts) {
		this.stmts = stmts;
	}
}
