package Semantics;

public interface Environment <K, V> {
    public void beginScope();
    public void endScope();
    public boolean inScope(K key);
    public boolean inCurrentScope(K key);
    public void add(K key, V value);
    public V lookup(K key);
}
