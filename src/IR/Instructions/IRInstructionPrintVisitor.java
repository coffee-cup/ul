package IR.Instructions;

import java.io.PrintStream;
import IR.*;

public class IRInstructionPrintVisitor implements IR.Instructions.Visitor<Void> {
    private PrintStream out;

    public IRInstructionPrintVisitor(PrintStream out) {
        this.out = out;
    }

    public Void visit(Temp t) {
        out.print("TEMP"); space();
        out.print(t.getNumber()); colon();
        out.print(t.getType().toIRString());

        if (t.getTempClass() == TempClass.PARAMETER || t.getTempClass() == TempClass.LOCAL) {
            numSpaces(4); openSquare();
            out.print(t.getTempClass().toString()); openParen();
            quote(); out.print(t.getName()); quote();
            closeParen(); closeSquare();
        }

        return null;
    }

    public Void visit(IRVarAssign i) {
        printTemp(i.getLeftOperand());
        equals();
        printTemp(i.getRightOperand());

        return null;
    }

    public Void visit(IRConstantAssign i) {
        printTemp(i.getOperand());
        equals();
        out.print(i.getConstant().toString());

        return null;
    }

    public Void visit(IRUnaryOp i) {
        printTemp(i.getDest());
        equals();
        out.print(i.getSource().getType().toIRString());
        out.print(i.getOperation().toString());
        space();
        printTemp(i.getSource());

        return null;
    }

    private void printTemp(Temp t) {
        out.print("T" + t.getNumber());
    }

    private void equals() {
        out.print(" := ");
    }

    private void numSpaces(int num) {
        for (int i = 0; i < num; i += 1) {
            space();
        }
    }

    private void space() {
        out.print(" ");
    }

    private void commaSep() {
        out.print(", ");
    }

    private void quote() {
        out.print("\"");
    }

    private void semi() {
        out.print(";");
    }

    private void colon() {
        out.print(":");
    }

    private void openSquare() {
        out.print("[");
    }

    private void closeSquare() {
        out.print("]");
    }

    private void openParen() {
        out.print("(");
    }

    private void closeParen() {
        out.print(")");
    }

}