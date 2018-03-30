package IR;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

import IR.Exceptions.MaxTempsExceededException;
import IR.Instructions.Temp;
import Types.Type;

public class TempFactory {
    private static final int maxTemps = 65535;

    private int currentNumber;
    private int scopeLevel;

    // Temps that are assigned to a parameter or local variable
    private LinkedList<HashMap<String, Temp>> scopedTemps;

    // Named Temps that have gone out of scope, but we still need to
    // know about for generating IR
    private LinkedList<Temp> outOfScopeTemps;

    // Temps that are just used once for the result of an expression
    private LinkedList<Temp> oneOffTemps;

    public TempFactory() {
        currentNumber = -1;
        scopeLevel = -1;
        scopedTemps = new LinkedList<HashMap<String, Temp>>();
        outOfScopeTemps = new LinkedList<Temp>();
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

    public Temp getNamedTemp(String name) {
        return lookupScopedTemp(name);
    }

    public Temp getScopedTemp(String name, Type type, TempClass tempClass, boolean create) {
        Temp t = create ? null : lookupScopedTemp(name);
        if (t == null) {
            t = createTemp(type, tempClass);
            getCurrentScope().put(name, t);
        }
        t.setName(name);
        return t;
    }

    public Temp getParamTemp(String name, Type type) {
        return getScopedTemp(name, type, TempClass.PARAMETER, false);
    }

    public Temp getLocalTemp(String name, Type type, boolean create) {
        return getScopedTemp(name, type, TempClass.LOCAL, create);
    }

    public Temp getLocalTemp(String name, Type type) {
        return getLocalTemp(name, type, false);
    }

    public void beginScope() {
        HashMap<String, Temp> scope = new HashMap<String, Temp>();
        scopedTemps.push(scope);
        scopeLevel += 1;
    }

    public void endScope() {
        outOfScopeTemps.addAll(getCurrentScope().values());
        scopedTemps.pop();
        scopeLevel -= 1;
    }

    public int getTempCount() {
        return getAllTemps().size();
    }

    public LinkedList<Temp> getAllTemps() {
        LinkedList<Temp> temps = new LinkedList<Temp>();

        for (HashMap<String, Temp> scope: scopedTemps) {
            for (Temp t: scope.values()) {
                temps.add(t);
            }
        }

        temps.addAll(outOfScopeTemps);
        temps.addAll(oneOffTemps);

        // Sort temps based on number
        Collections.sort(temps, new Comparator<Temp>() {
            @Override
            public int compare(Temp t1, Temp t2) {
                return Integer.compare(t1.getNumber(), t2.getNumber());
            }
        });

        return temps;
    }

    private HashMap<String, Temp> getCurrentScope () {
        return scopedTemps.size() > 0 ? scopedTemps.get(0): null;
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
