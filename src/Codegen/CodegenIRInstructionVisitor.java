package Codegen;

import IR.Instructions.*;
import IR.Constants.*;
import Types.*;

public class CodegenIRInstructionVisitor implements IR.Instructions.Visitor<Void> {
    private StringBuilder out;
    private String className;
    private int currentStackSize;
    private int maxStackSize;
    private int labelNum;

    public CodegenIRInstructionVisitor(StringBuilder out, String className) {
        this.out = out;
        this.className = className;

        currentStackSize = 0;
        maxStackSize = 0;
        labelNum = 0;
    }

    public Void visit(Temp t) {
        stackSet(0, 1);
        if (t.isParam()) {
            return null;
        }

        if (StringType.check(t.getType()) || ArrayType.check(t.getType())) {
            typeInstr(t, "const_null");
            store(t);
        } else if (FloatType.check(t.getType())) {
            pushConstant(new IRFloatConstant(0));
            store(t);
        } else {
            pushConstant(new IRIntegerConstant(0));
            store(t);
        }

        return null;
    }

    public Void visit(IRVarAssign i) {
        stackSet(0, 1);
        load(i.getRightOperand());
        store(i.getLeftOperand());

        return null;
    }

    public Void visit(IRConstantAssign i) {
        stackSet(0, 1);
        pushConstant(i.getConstant());
        store(i.getOperand());

        return null;
    }

    public Void visit(IRUnaryOp i) {
        return null;
    }

    public Void visit(IRBinaryOp i) {
        if (!StringType.check(i.getDest().getType())) {
            stackSet(0, 2);
            load(i.getLeftOperand());
            load(i.getRightOperand());

            if (i.getOperation().hasJVMInstr()) {
                typeInstr(i.getDest(), i.getOperation().toJVMInstr());
                store(i.getDest());
            } else {
                if (i.getOperation() == IRBOp.LESSTHAN) {
                    binOpIf("lt", i.getDest());
                } else if (i.getOperation() == IRBOp.DOUBEQ) {
                    binOpIf("eq", i.getDest());
                }
            }
        } else {
            
        }

        return null;
    }

    private void binOpIf(String op, Temp dest) {
        String l1 = freshLabel();
        String l2 = freshLabel();

        typeInstr(dest, "sub");
        instr("if" + op + " " + l1);
        pushConstant(new IRIntegerConstant(0));
        gotoInstr(l2);
        label(l1);
        pushConstant(new IRIntegerConstant(1));
        label(l2);
        store(dest);
    }

    public Void visit(IRArrayCreation i) {
        stackSet(0, 1);

        pushConstant(new IRIntegerConstant(2));
        Type typeOf = i.getArr().getArrayOfType();
        String stringType = typeOf.toString();
        if (StringType.check(typeOf)) {
            stringType = "java/lang/String";
            typeInstr(typeOf, "newarray " + stringType);
        } else {
            instr("newarray " + stringType);
        }
        store(i.getDest());

        return null;
    }

    public Void visit(IRArrayReference i) {
        return null;
    }

    public Void visit(IRArrayAssign i) {
        load(i.getDest());
        load(i.getRefTemp());
        load(i.getAssignTemp());
        typeInstr(i.getAssignTemp(), "astore", false);

        return null;
    }

    public Void visit(IRPrint i) {
        stackSet(0, 1);

        getPrint();
        load(i.getTemp());

        String funcSignature = "(" + i.getTemp().getType().toJVMString() + ")V";
        instr("invokevirtual java/io/PrintStream/print(" + funcSignature);

        return null;
    }

    public Void visit(IRPrintln i) {
        stackSet(0, 1);

        getPrint();
        load(i.getTemp());

        String funcSignature = "(" + i.getTemp().getType().toJVMString() + ")V";
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
        for (Temp t: i.getArgs()) {
            load(t);
        }

        String argString = "";
        for (Temp t: i.getArgs()) {
            argString += t.getType().toJVMString();
        }
        String returnString = "V";
        if (i.getTemp() != null) {
            returnString = i.getTemp().getType().toJVMString();
        }
        String funcSignature = i.getName() + "(" + argString + ")" + returnString;


        instr("invokestatic " + className + "/" + funcSignature);
        if (i.getTemp() != null) {
            store(i.getTemp());
        }

        stackSet(i.getArgs().size(), i.getTemp() == null ? 0 : 1);

        return null;
    }

    public Void visit(IRLabel i) {
        label("L" + i.getNumber());

        return null;
    }

    public Void visit(IRGoto i) {
        gotoInstr("L" + i.getJump().getNumber());

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

    private void pushConstant(IRConstant c) {
        instr("ldc " + c.toJVMString());
    }

    private void getPrint() {
        instr("getstatic java/lang/System/out Ljava/io/PrintStream;");
    }

    private String freshLabel() {
        labelNum += 1;
        return "L_" + (labelNum - 1);
    }

    private void label(String l) {
        out.append(l + ":\n");
    }

    private void gotoInstr(String l) {
        instr("goto " + l);
    }

    private void typeInstr(Type t, String s, boolean subcode) {
        String stringCode = subcode ? t.toJVMSubCode() : t.toJVMCode();
        instr(stringCode + s);
    }

    private void typeInstr(Temp t, String s, boolean subcode) {
        typeInstr(t.getType(), s, subcode);
    }

    private void typeInstr(Type t, String s) {
        typeInstr(t, s, true);
    }

    private void typeInstr(Temp t, String s) {
        typeInstr(t.getType(), s);
    }

    private void instr(String s) {
        out.append("    ");
        out.append(s);
        out.append("\n");
    }
}
