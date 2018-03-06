package IR.Instructions;

public interface Visitor<T> {
    public T visit(Temp t);
}
