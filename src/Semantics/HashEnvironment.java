package Semantics;

import java.util.LinkedList;
import java.util.HashMap;

public class HashEnvironment<K, V> implements Environment<K, V> {
    LinkedList<HashMap<K, V>> scopes;
    int scopeLevel = 0;

	public HashEnvironment() {
        scopes = new LinkedList<HashMap<K, V>>();
		scopeLevel = 0;
	}

	public void beginScope() {
        HashMap<K, V> scope = new HashMap<K, V>();
        scopes.push(scope);
        scopeLevel += 1;
	}

	public void endScope() {
        scopes.pop();
        scopeLevel -= 1;
	}

    public HashMap<K, V> getCurrentScope() {
        return scopes.get(scopes.size() - 1);
    }

    public boolean inScope(K key) {
        return lookup(key) != null;
    }

	public boolean inCurrentScope(K key) {
        HashMap<K, V> currentScope = getCurrentScope();
        return currentScope.get(key) != null;
	}

	public void add(K key, V value) {
        getCurrentScope().put(key, value);
	}

	public V lookup(K key) {
        for (HashMap<K, V> scope: scopes) {
            V value = scope.get(key);
            if (value != null) {
                return value;
            }
        }
        return null;
	}
}
