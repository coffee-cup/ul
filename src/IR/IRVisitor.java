package IR;

import java.util.LinkedList;

import AST.*;
import Types.*;
import IR.Constants.*;
import IR.Instructions.*;

public class IRVisitor implements AST.Visitor<Temp> {
    private String sourceFilename;
    private IRProgram irProgram;

    private IRFunction currentFunction;
    private LinkedList<IRInstruction> instrs;
    private TempFactory temps;

    public IRVisitor(String sourceFilename) {
        this.sourceFilename = sourceFilename;
    }

    public Temp visit(AddExpression e) {
        return null;
    }

    public Temp visit(AssignStatement s) {
        Temp tLeft = s.getName().accept(this);
        Temp tRight = s.getExpr().accept(this);

        // TODO: Type casting
        IRInstruction in = new IRVarAssign(tLeft, tRight);
        instrs.add(in);

        return null;
    }

    public Temp visit(ArrayAssignStatement s) {
        return null;
    }

    public Temp visit(ArrayReference a) {
        return null;
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
        return null;
    }

    public Temp visit(ExpressionStatement e) {
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
        currentFunction = new IRFunction();
        instrs = currentFunction.getInstructions();
        temps = currentFunction.getTempFactory();
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
        return null;
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
        return null;
    }

    public Temp visit(MultExpression e) {
        return null;
    }

    public Temp visit(ParenExpression p) {
        return null;
    }

    public Temp visit(PrintStatement s) {
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
        return null;
    }

    public Temp visit(StringLiteral s) {
        Temp t = temps.getTemp(s.getType());
        IRConstant stringConstant = new IRStringConstant(s.getValue());
        IRInstruction in = new IRConstantAssign(t, stringConstant);
        instrs.add(in);

        return null;
    }

    public Temp visit(SubExpression e) {
        return null;
    }

    public Temp visit(TypeNode t) {
        return null;
    }

    public Temp visit(VariableDeclaration v) {
        return temps.getLocalTemp(v.getIdent().getName(), v.getType());
    }

    public Temp visit(WhileStatement s) {
        return null;
    }

    public IRProgram getIRProgram() {
        return irProgram;
    }
}
