package IR.Instructions;

import java.io.PrintStream;
import IR.*;

public class IRInstructionPrintVisitor implements IR.Instructions.Visitor<Void> {
    private PrintStream out;

    public IRInstructionPrintVisitor(PrintStream out) {
        this.out = out;
    }
}
