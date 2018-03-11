package IR;

import java.util.LinkedList;
import IR.Instructions.*;

public class IRFunction extends IRNode {
    private String name;
    private String signature;
    private LinkedList<IRInstruction> instrs;
    private TempFactory tempFactory;

    public IRFunction() {
        instrs = new LinkedList<IRInstruction>();
        tempFactory = new TempFactory();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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