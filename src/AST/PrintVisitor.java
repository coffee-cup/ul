package AST;

import Types.*;
import AST.*;
import java.io.PrintStream;

public class PrintVisitor implements Visitor {
    PrintStream out;

    public PrintVisitor() {
        out = System.out;
    }

    public void visit(Program p) {
        out.println("Program");
    }
}

