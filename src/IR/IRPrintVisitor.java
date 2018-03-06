package IR;

import java.io.PrintStream;

import IR.Instructions.IRInstruction;
import IR.Instructions.IRInstructionPrintVisitor;

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

        for (IRInstruction i: f.getInstructions()) {
            printIndent();
            IRInstructionPrintVisitor irInstructionPrintVisitor =
                new IRInstructionPrintVisitor(out);
            i.accept(irInstructionPrintVisitor);
            newLine();
        }
        if (f.getInstructions().size() == 0) newLine();

        backIndent(); closeBrace();

        return null;
    }

    public Void visit(IRProgram p) {
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
