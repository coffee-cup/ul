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
        out.print(t.getType().toIRString()); semi();

        return null;
    }

    private void space() {
        out.print(" ");
    }

    private void commaSep() {
        out.print(", ");
    }

    private void semi() {
        out.print(";");
    }

    private void colon() {
        out.print(":");
    }
}
