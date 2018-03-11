package IR;

import java.util.LinkedList;

import IR.Instructions.Label;

public class LabelFactory {
    private int currentNumber;
    private LinkedList<Label> labels;

    public LabelFactory() {
        labels = new LinkedList<Label>();
        currentNumber = 0;
    }

    public Label getLabel() {
        currentNumber += 1;
        Label l = new Label(currentNumber);
        return l;
    }
}
