package IR;

import java.util.LinkedList;

import IR.Instructions.IRLabel;

public class LabelFactory {
    private int currentNumber;
    private LinkedList<IRLabel> labels;

    public LabelFactory() {
        labels = new LinkedList<IRLabel>();
        currentNumber = -1;
    }

    public IRLabel getLabel() {
        currentNumber += 1;
        IRLabel l = new IRLabel(currentNumber);
        return l;
    }
}
