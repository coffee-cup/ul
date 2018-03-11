package IR;

import java.io.PrintStream;

import IR.Instructions.IRInstruction;
import IR.Instructions.IRInstructionPrintVisitor;
import IR.Instructions.IRLabel;

public class IRPrintVisitor implements IR.Visitor<Void> {
    private PrintStream out;
    private int indentLevel = 0;

    public IRPrintVisitor(PrintStream out) {
        this.out = out;
    }

    public Void visit(IRFunction f) {
        out.print("FUNC"); space();
        out.print(f.getName()); space();
        out.print(f.getSignature()); newLine();
        openBrace(); forwardIndent();

        // Print visitor for instructions
        IRInstructionPrintVisitor irInstructionPrintVisitor =
            new IRInstructionPrintVisitor(out);

        // Temp Creation
        for (IRInstruction i: f.getTempFactory().getAllTemps()) {
            newLine(); printIndent();
            i.accept(irInstructionPrintVisitor);
            semi();
        }

        newLine();

        // Instructions
        for (IRInstruction i: f.getInstructions()) {
            newLine(); printIndent();

            boolean isLabel = (i instanceof IRLabel);
            if (!isLabel) printIndent();
            i.accept(irInstructionPrintVisitor);
            semi();
        }

        backIndent(); newLine(); closeBrace();

        return null;
    }

    public Void visit(IRProgram p) {
        out.print("PROG"); space();
        out.print(p.getName()); newLine();
        for (IRFunction f: p.getFunctions()) {
            f.accept(this);
            newLine(); newLine();
        }

        return null;
    }

    private void forwardIndent() {
        indentLevel += 1;
    }

    private void backIndent() {
        indentLevel -= 1;
        if (indentLevel < 0)
            indentLevel = 0;
    }

    private void printIndent() {
        for (int i = 0; i < indentLevel; i += 1) {
            out.print("    ");
        }
    }

    private void openBrace() {
        out.print("{");
    }

    private void closeBrace() {
        out.print("}");
    }

    private void newLine() {
        out.println("");
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
}
