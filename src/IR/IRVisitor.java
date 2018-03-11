package IR;

import java.util.ArrayList;
import java.util.LinkedList;

import AST.*;
import Types.*;
import IR.Constants.*;
import IR.Exceptions.IRException;
import IR.Instructions.*;

public class IRVisitor implements AST.Visitor<Temp> {
    private String sourceFilename;
    private IRProgram irProgram;

    private FunctionDecl currentFunctionDecl;
    private IRFunction currentFunction;
    private LinkedList<IRInstruction> instrs;
    private TempFactory temps;
    private LabelFactory labels;

    public IRVisitor(String sourceFilename) {
        this.sourceFilename = sourceFilename;
    }

    public Temp visit(AddExpression e) {
        return binaryOperation(e, null, IRBOp.ADD);
    }

    public Temp visit(AssignStatement s) {
        Temp tLeft = s.getName().accept(this);
        Temp tRight = s.getExpr().accept(this);

        IRInstruction in = new IRVarAssign(tLeft, typeCast(tRight, tLeft.getType()));
        instrs.add(in);

        return null;
    }

    public Temp visit(ArrayAssignStatement s) {
        Temp t = temps.getNamedTemp(s.getName().getName());
        Temp refTemp = s.getRefExpr().accept(this);
        Temp assignTemp = s.getAssignExpr().accept(this);

        Type requiredType = ((ArrayType)t.getType()).getArrayOfType();
        IRInstruction in = new IRArrayAssign(t, refTemp, typeCast(assignTemp, requiredType));
        instrs.add(in);

        return null;
    }

    public Temp visit(ArrayReference a) {
        Temp arrayTemp = temps.getNamedTemp(a.getName().getName());
        Temp refTemp = a.getExpr().accept(this);

        Type arrayOfType = ((ArrayType)arrayTemp.getType()).getArrayOfType();
        Temp t = temps.getTemp(arrayOfType);

        IRInstruction in = new IRArrayReference(t, arrayTemp, refTemp);
        instrs.add(in);

        return t;
    }

    public Temp visit(Block b) {
        temps.beginScope();
        for (Statement s: b.getStmts()) {
            s.accept(this);
        }
        temps.endScope();

        return null;
    }

    public Temp visit(BooleanLiteral b) {
        Temp t = temps.getTemp(b.getType());
        IRConstant booleanConstant = new IRBooleanConstant(b.getValue());
        IRInstruction in = new IRConstantAssign(t, booleanConstant);
        instrs.add(in);

        return t;
    }

    public Temp visit(CharacterLiteral c) {
        Temp t = temps.getTemp(c.getType());
        IRConstant charConstant = new IRCharacterConstant(c.getValue());
        IRInstruction in = new IRConstantAssign(t, charConstant);
        instrs.add(in);

        return t;
    }

    public Temp visit(EqualityExpression e) {
        return binaryOperation(e, BooleanType.getInstance(), IRBOp.DOUBEQ);
    }

    public Temp visit(ExpressionStatement e) {
        e.getExpr().accept(this);

        return null;
    }

    public Temp visit(FloatLiteral f) {
        Temp t = temps.getTemp(f.getType());
        IRConstant floatConstant = new IRFloatConstant(f.getValue());
        IRInstruction in = new IRConstantAssign(t, floatConstant);
        instrs.add(in);

        return t;
    }

    public Temp visit(FormalParameter p) {
        return temps.getParamTemp(p.getIdent().getName(), p.getType());
    }

    public Temp visit(Function f) {
        currentFunctionDecl = f.getDecl();
        currentFunction = new IRFunction();
        instrs = currentFunction.getInstructions();
        temps = currentFunction.getTempFactory();
        labels = currentFunction.getLabelFactory();
        irProgram.getFunctions().add(currentFunction);

        temps.beginScope();

        f.getDecl().accept(this);
        f.getBody().accept(this);

        temps.endScope();

        return null;
    }

    public Temp visit(FunctionBody f) {
        for (Statement s: f.getBlock().getStmts()) {
            s.accept(this);
        }

        return null;
    }

    public Temp visit(FunctionCall f) {
        // Get the function declaration we tagged in type checking
        FunctionDecl decl = f.getDeclCalled();

        Temp t = null;
        if (!VoidType.check(decl.getType())) {
            t = temps.getTemp(decl.getType());
        }

        ArrayList<Temp> args = new ArrayList<Temp>();
        for (int i = 0; i < f.getParams().size(); i += 1) {
            // Type cast any args to what they need to be for the call
            Temp arg = f.getParams().get(i).accept(this);
            Type required = decl.getParams().get(i).getType();
            args.add(typeCast(arg, required));
        }

        IRInstruction in = new IRFunctionCall(f.getName().getName(), t, args);
        instrs.add(in);

        return t;
    }

    public Temp visit(FunctionDecl f) {
        currentFunction.setName(f.getIdent().getName());

        String sig = "(";
        for (FormalParameter fp: f.getParams()) {
            sig += fp.getType().toIRString();
        }
        sig += ")" + f.getType().toIRString();

        currentFunction.setSignature(sig);

        for (FormalParameter p: f.getParams()) {
            p.accept(this);
        }

        return null;
    }

    public Temp visit(Identifier i) {
        return temps.getNamedTemp(i.getName());
    }

    public Temp visit(IfStatement i) {
        return null;
    }

    public Temp visit(IntegerLiteral i) {
        Temp t = temps.getTemp(i.getType());
        IRConstant intConstant = new IRIntegerConstant(i.getValue());
        IRInstruction in = new IRConstantAssign(t, intConstant);
        instrs.add(in);

        return t;
    }

    public Temp visit(LessThanExpression e) {
        return binaryOperation(e, BooleanType.getInstance(), IRBOp.LESSTHAN);
    }

    public Temp visit(MultExpression e) {
        return binaryOperation(e, null, IRBOp.MULT);
    }

    public Temp visit(ParenExpression p) {
        return p.getExpr().accept(this);
    }

    public Temp visit(PrintStatement s) {
        Temp t = s.getExpr().accept(this);

        IRInstruction in;
        if (s.isNewline()) {
            in = new IRPrintln(t);
        } else {
            in = new IRPrint(t);
        }
        instrs.add(in);

        return null;
    }

    public Temp visit(Program p) {
        irProgram = new IRProgram(sourceFilename);
        for (Function f: p.getFunctions()) {
            f.accept(this);
        }

        return null;
    }

    public Temp visit(ReturnStatement s) {
        IRInstruction in;
        if (s.getExpr() == null) {
            in = new IRReturn();
        } else {
            Temp t = s.getExpr().accept(this);
            in = new IRReturn(typeCast(t, currentFunctionDecl.getType()));
        }
        instrs.add(in);

        return null;
    }

    public Temp visit(StringLiteral s) {
        Temp t = temps.getTemp(s.getType());
        IRConstant stringConstant = new IRStringConstant(s.getValue());
        IRInstruction in = new IRConstantAssign(t, stringConstant);
        instrs.add(in);

        return t;
    }

    public Temp visit(SubExpression e) {
        return binaryOperation(e, null, IRBOp.SUB);
    }

    public Temp visit(TypeNode t) {
        return null;
    }

    public Temp visit(VariableDeclaration v) {
        Temp t = temps.getLocalTemp(v.getIdent().getName(), v.getType());

        if (ArrayType.check(v.getType())) {
            IRInstruction in = new IRArrayCreation(t, (ArrayType)v.getType());
            instrs.add(in);
        }

        return t;
    }

    public Temp visit(WhileStatement s) {
        return null;
    }

    public Temp binaryOperation(OperatorExpression e, Type operatorType, IRBOp operation) {
        Temp left = e.getLeftExpr().accept(this);
        Temp right = e.getRightExpr().accept(this);

        Type greaterType = Type.greaterType(left.getType(), right.getType());
        if (operatorType == null) {
            operatorType = greaterType;
        }
        Temp dest = temps.getTemp(operatorType);
        IRInstruction in = new IRBinaryOp(dest,
                                          typeCast(left, greaterType),
                                          typeCast(right, greaterType),
                                          operation);
        instrs.add(in);

        return dest;
    }

    // Adds type casting instructions if necessary
    // Ensures returns a temp that is guaranteed to be of type required
    private Temp typeCast(Temp t, Type required) {
        if (t.getType().equals(required)) {
            return t;
        }

        if (!(FloatType.check(required) && IntegerType.check(t.getType()))) {
            throw new IRException("Cannot perform type cast from " +
                                  t.getType().toString() + " to " + required.toString());
        }

        Temp newT = temps.getTemp(required);

        IRInstruction in = new IRUnaryOp(newT, t, IRUOp.TOFLOAT);
        instrs.add(in);

        return newT;
    }

    public IRProgram getIRProgram() {
        return irProgram;
    }
}
