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

    public Void visit(IRProgram p) {
        return null;
    }

    public Void visit(IRFunction f) {
        for (IRInstruction i: f.getInstructions()) {
            IRInstructionPrintVisitor irInstructionPrintVisitor =
                new IRInstructionPrintVisitor(out);
            i.accept(irInstructionPrintVisitor);
        }

        return null;
    }
}
