package IR.Instructions;

public interface Visitor<T> {
    public T visit(Temp t);
    public T visit(IRVarAssign i);
    public T visit(IRConstantAssign i);
    public T visit(IRUnaryOp i);
    public T visit(IRBinaryOp i);
    public T visit(IRArrayCreation i);
    public T visit(IRArrayReference i);
    public T visit(IRArrayAssign i);
    public T visit(IRPrint i);
    public T visit(IRPrintln i);
    public T visit(IRReturn i);
    public T visit(IRFunctionCall i);
    public T visit(IRLabel i);
    public T visit(IRGoto i);
    public T visit(IRIfStatement i);
}
