package Codegen;

import IR.Instructions.*;
import Types.*;

public class CodegenIRInstructionVisitor implements IR.Instructions.Visitor<Void> {
    private StringBuilder out;
    private int currentStackSize;
    private int maxStackSize;

    public CodegenIRInstructionVisitor(StringBuilder out) {
        this.out = out;

        currentStackSize = 0;
        maxStackSize = 0;
    }

    public Void visit(Temp t) {
        stackSet(0, 1);
        if (t.isParam()) {
            return null;
        }

        if (StringType.check(t.getType())) {
        } else if (FloatType.check(t.getType())) {
            instr("ldc 0.0");
            store(t);
        } else {
            instr("ldc 0");
            store(t);
        }

        return null;
    }

    public Void visit(IRVarAssign i) {
        return null;
    }

    public Void visit(IRConstantAssign i) {
        return null;
    }

    public Void visit(IRUnaryOp i) {
        return null;
    }

    public Void visit(IRBinaryOp i) {
        return null;
    }

    public Void visit(IRArrayCreation i) {
        return null;
    }

    public Void visit(IRArrayReference i) {
        return null;
    }

    public Void visit(IRArrayAssign i) {
        return null;
    }

    public Void visit(IRPrint i) {
        stackSet(0, 1);
        getPrint();
        load(i.getTemp());

        String funcSignature = "(" + i.getTemp().getType() + ")V";
        instr("invokevirtual java/io/PrintStream/print(" + funcSignature);

        return null;
    }

    public Void visit(IRPrintln i) {
        stackSet(0, 1);
        getPrint();
        load(i.getTemp());

        String funcSignature = "(" + i.getTemp().getType().toIRString() + ")V";
        instr("invokevirtual java/io/PrintStream/println" + funcSignature);

        return null;
    }

    public Void visit(IRReturn i) {
        if (i.getTemp() != null) {
            stackSet(0, 1);
            load(i.getTemp());
            typeInstr(i.getTemp(), "return");
        } else {
            instr("return");
        }
        return null;
    }

    public Void visit(IRFunctionCall i) {
        return null;
    }

    public Void visit(IRLabel i) {
        return null;
    }

    public Void visit(IRGoto i) {
        return null;
    }

    public Void visit(IRIfStatement i) {
        return null;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    // When calling this function we are increasing the stack size by current
    //   but the required stack will actually increase by max
    private void stackSet(int current, int max) {
        if (currentStackSize + max > maxStackSize) {
            maxStackSize = currentStackSize + max;
        }
        currentStackSize += current;
    }

    private void stackSet(int current) {
        stackSet(current, current);
    }

    private void load(Temp t) {
        typeInstr(t, "load " + t.getNumber());
    }

    private void store(Temp t) {
        typeInstr(t, "store " + t.getNumber());
    }

    private void getPrint() {
        instr("getstatic java/lang/System/out Ljava/io/PrintStream;");
    }

    private void typeInstr(Temp t, String s) {
        instr(t.getType().toJVMString() + s);
    }

    private void instr(String s) {
        out.append("    ");
        out.append(s);
        out.append("\n");
    }
}
