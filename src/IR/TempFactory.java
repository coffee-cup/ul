package IR;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import IR.Exceptions.MaxTempsExceededException;
import IR.Instructions.Temp;
import Types.Type;

public class TempFactory {
    private static final int maxTemps = 65535;

    private int currentNumber;
    private LinkedList<HashMap<String, Temp>> scopedTemps;
    private LinkedList<Temp> oneOffTemps;
    private int scopeLevel;

    public TempFactory() {
        currentNumber = 0;
        scopeLevel = -1;
        scopedTemps = new LinkedList<HashMap<String, Temp>>();
        oneOffTemps = new LinkedList<Temp>();
    }

    public Temp createTemp(Type type, TempClass tempClass) {
        if (currentNumber + 1 >= maxTemps) {
            throw new MaxTempsExceededException();
        }

        currentNumber += 1;
        Temp t = new Temp(currentNumber, type, tempClass);
        return t;
    }

    public Temp getTemp(Type type) {
        Temp t = createTemp(type, TempClass.TEMP);
        oneOffTemps.add(t);
        return t;
    }

    public Temp getScopedTemp(String name, Type type, TempClass tempClass) {
        Temp t = lookupScopedTemp(name);
        if (t == null) {
            t = createTemp(type, tempClass);
            getCurrentScope().put(name, t);
        }
        return t;
    }

    public Temp getParamTemp(String name, Type type) {
        return getScopedTemp(name, type, TempClass.PARAMETER);
    }

    public Temp getLocalTemp(String name, Type type) {
        return getScopedTemp(name, type, TempClass.LOCAL);
    }

    public void beginScope() {
        HashMap<String, Temp> scope = new HashMap<String, Temp>();
        scopedTemps.push(scope);
        scopeLevel += 1;
    }

    public void endScope() {
        scopeLevel -= 1;
    }

    public LinkedList<Temp> getAllTemps() {
        LinkedList<Temp> temps = new LinkedList<Temp>();

        for (Iterator<HashMap<String, Temp>> it = scopedTemps.descendingIterator(); it.hasNext();) {
            HashMap<String, Temp> scope = it.next();
            for (Temp t: scope.values()) {
                temps.add(t);
            }
        }

        for (Temp t: oneOffTemps) {
            temps.add(t);
        }

        return temps;
    }

    private HashMap<String, Temp> getCurrentScope () {
        return scopedTemps.size() > 0 ? scopedTemps.get(scopeLevel): null;
    }

    private Temp lookupScopedTemp(String name) {
        Temp t = null;
        for (HashMap<String, Temp> scope: scopedTemps) {
            t = scope.get(name);
            if (t != null) break;
        }
        return t;
    }
}
