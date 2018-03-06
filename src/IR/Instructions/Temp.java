package IR.Instructions;

import Types.Type;
import IR.*;

public class Temp extends IRInstruction {
    private int number;
    private Type type;
    private TempClass tempClass;

    public Temp(int number, Type type, TempClass tempClass) {
        this.number = number;
        this.type = type;
        this.tempClass = tempClass;
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

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
