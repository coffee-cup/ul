package Codegen;

import IR.Instructions.*;

public class CodegenIRInstructionVisitor implements IR.Instructions.Visitor<Integer> {
    private StringBuilder out;

    public CodegenIRInstructionVisitor(StringBuilder out) {
        this.out = out;
    }

    public Integer visit(Temp t) {
        return 0;
    }

    public Integer visit(IRVarAssign i) {
        return 0;
    }

    public Integer visit(IRConstantAssign i) {
        return 0;
    }

    public Integer visit(IRUnaryOp i) {
        return 0;
    }

    public Integer visit(IRBinaryOp i) {
        return 0;
    }

    public Integer visit(IRArrayCreation i) {
        return 0;
    }

    public Integer visit(IRArrayReference i) {
        return 0;
    }

    public Integer visit(IRArrayAssign i) {
        return 0;
    }

    public Integer visit(IRPrint i) {
        return 0;
    }

    public Integer visit(IRPrintln i) {
        return 0;
    }

    public Integer visit(IRReturn i) {
        return 0;
    }

    public Integer visit(IRFunctionCall i) {
        return 0;
    }

    public Integer visit(IRLabel i) {
        return 0;
    }

    public Integer visit(IRGoto i) {
        return 0;
    }

    public Integer visit(IRIfStatement i) {
        return 0;
    }

    private void print(String s) {
        out.append("    ");
        out.append(s);
    }
}
