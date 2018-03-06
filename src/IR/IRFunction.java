package IR;

import java.util.LinkedList;
import IR.Instructions.*;

public class IRFunction extends IRNode {
    private String name;
    private String signature;
    private LinkedList<IRInstruction> instrs;
    private TempFactory tempFactory;

    public IRFunction(String name, String signature) {
        this.name = name;
        this.signature = signature;

        instrs = new LinkedList<IRInstruction>();
        tempFactory = new TempFactory();
    }

    public String getName() {
        return name;
    }

    public String getSignature() {
        return signature;
    }

    public LinkedList<IRInstruction> getInstructions() {
        return instrs;
    }

    public TempFactory getTempFactory() {
        return tempFactory;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
