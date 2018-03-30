package IR;

import java.util.LinkedList;

import IR.Instructions.IRInstruction;
import Types.Type;

public class IRFunction extends IRNode {
    private String name;
    private String signature;
    private LinkedList<IRInstruction> instrs;
    private TempFactory tempFactory;
    private LabelFactory labelFactory;

    private LinkedList<Type> paramTypes;
    private Type returnType;

    public IRFunction() {
        instrs = new LinkedList<IRInstruction>();
        tempFactory = new TempFactory();
        labelFactory = new LabelFactory();

        paramTypes = null;
        returnType = null;
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

    public LabelFactory getLabelFactory() {
        return labelFactory;
    }

    public LinkedList<Type> getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(LinkedList<Type> paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
