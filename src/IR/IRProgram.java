package IR;

import java.util.LinkedList;

public class IRProgram extends IRNode {
    private String name;
    private LinkedList<IRFunction> functions;

    public IRProgram(String name) {
        this.name = name;

        functions = new LinkedList<IRFunction>();
    }

    public String getName() {
        return name;
    }

    public LinkedList<IRFunction> getFunctions() {
        return functions;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
