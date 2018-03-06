package IR;

import java.util.ArrayList;

import IR.Exceptions.MaxTempsExceededException;
import IR.Instructions.*;
import Types.Type;

public class TempFactory {
    private int maxTemps = 65535;

    private int currentNumber;
    private ArrayList<Temp> temps;

    public TempFactory() {
        currentNumber = 0;
        temps = new ArrayList<Temp>();
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
        return createTemp(type, TempClass.TEMP);
    }

    public Temp getParamTemp(Type type) {
        return createTemp(type, TempClass.PARAMETER);
    }

    public Temp getLocalTemp(Type type) {
        return createTemp(type, TempClass.LOCAL);
    }

    public ArrayList<Temp> getAllTemps() {
        return temps;
    }
}
