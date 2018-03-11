package IR.Instructions;

import Types.ArrayType;

public class IRArrayCreation extends IRInstruction {
    private Temp dest;
    private ArrayType arr;

    public IRArrayCreation(Temp dest, ArrayType arr) {
        this.dest = dest;
        this.arr = arr;
    }

    public Temp getDest() {
        return dest;
    }

    public ArrayType getArr() {
        return arr;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
