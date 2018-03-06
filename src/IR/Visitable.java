package IR;

public interface Visitable {
    public <T> T accept(Visitor<T> v);
}
