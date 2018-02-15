package AST;

import java.util.ArrayList;

public class Program extends ASTNode {
    private ArrayList<Function> functions;
    private String filename;

    public Program() {
        setFunctions(new ArrayList<Function>());
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

	public ArrayList<Function> getFunctions() {
		return functions;
	}

	public void setFunctions(ArrayList<Function> functions) {
		this.functions = functions;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
