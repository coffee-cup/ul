package IR;

import java.util.LinkedList;

import AST.*;
import Types.*;
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
        return null;
    }

    public Temp visit(CharacterLiteral c) {
        return null;
    }

    public Temp visit(EqualityExpression e) {
        return null;
    }

    public Temp visit(ExpressionStatement e) {
        return null;
    }

    public Temp visit(FloatLiteral f) {
        return null;
    }

    public Temp visit(FormalParameter p) {
        return null;
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

        return null;
    }

    public Temp visit(Identifier i) {
        return null;
    }

    public Temp visit(IfStatement i) {
        return null;
    }

    public Temp visit(IntegerLiteral i) {
        return null;
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
