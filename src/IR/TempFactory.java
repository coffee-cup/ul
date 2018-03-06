package IR;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import IR.Exceptions.MaxTempsExceededException;
import IR.Instructions.Temp;
import Types.Type;

public class TempFactory {
    private int maxTemps = 65535;

    private int currentNumber;
    private HashMap<String, Temp> paramTemps;
    private HashMap<String, Temp> localTemps;
    private LinkedList<Temp> oneOffTemps;

    public TempFactory() {
        currentNumber = 0;
        paramTemps = new HashMap<String, Temp>();
        localTemps = new HashMap<String, Temp>();
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

    public Temp getParamTemp(String name, Type type) {
        Temp t = paramTemps.get(name);
        if (t == null) {
            t = createTemp(type, TempClass.PARAMETER);
            paramTemps.put(name, t);
        }
        return t;
    }

    public Temp getLocalTemp(String name, Type type) {
        Temp t = localTemps.get(name);
        if (t == null) {
            t = createTemp(type, TempClass.LOCAL);
            localTemps.put(name, t);
        }
        return t;
    }

    public LinkedList<Temp> getAllTemps() {
        LinkedList<Temp> temps = new LinkedList<Temp>();

        for (Temp t: paramTemps.values()) {
            temps.add(t);
        }
        for (Temp t: localTemps.values()) {
            temps.add(t);
        }
        for (Temp t: oneOffTemps) {
            temps.add(t);
        }

        return temps;
    }
}
