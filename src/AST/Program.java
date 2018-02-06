package AST;

import java.util.ArrayList;
import AST.*;

public class Program extends ASTNode {
    public ArrayList<Function> functions;
    String filename;

    public Program() {
        functions = new ArrayList<Function>();
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
