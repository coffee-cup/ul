package IR;

public interface Visitor<T> {
    public T visit(IRProgram p);
    public T visit(IRFunction f);
}
