package IR.Instructions;

import Types.Type;
import IR.*;

public class Temp extends IRInstruction {
    private int number;
    private Type type;
    private TempClass tempClass;
    private String name;

    public Temp(int number, Type type, TempClass tempClass) {
        this(number, type, tempClass, "");
    }

    public Temp(int number, Type type, TempClass tempClass, String name) {
        this.number = number;
        this.type = type;
        this.tempClass = tempClass;
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public Type getType() {
        return type;
    }

    public TempClass getTempClass() {
        return tempClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isParam() {
        return tempClass == TempClass.PARAMETER;
    }

    public boolean isLocal() {
        return tempClass == TempClass.LOCAL;
    }

    public boolean isParamOrLocal() {
        return isParam() || isLocal();
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
