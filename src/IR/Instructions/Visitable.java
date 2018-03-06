package IR.Instructions;

public interface Visitable {
    public <T> T accept(IR.Instructions.Visitor<T> v);
}
