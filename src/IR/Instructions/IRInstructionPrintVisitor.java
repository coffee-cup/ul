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

        semi();

        return null;
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
