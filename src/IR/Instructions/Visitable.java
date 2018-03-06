package IR.Instructions;

public interface Visitable {
    public <T> T accept(Visitor<T> v);
}
