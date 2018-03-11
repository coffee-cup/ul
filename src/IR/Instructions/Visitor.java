package IR.Instructions;

public interface Visitor<T> {
    public T visit(Temp t);
    public T visit(IRVarAssign i);
    public T visit(IRConstantAssign i);
    public T visit(IRUnaryOp i);
    public T visit(IRBinaryOp i);
}
