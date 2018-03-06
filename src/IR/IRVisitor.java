package IR;

import java.util.ArrayList;
import AST.*;
import Types.*;
import IR.Instructions.*;

public class IRVisitor implements AST.Visitor<Temp> {
    private IRProgram irProgram;

    private ArrayList<IRInstruction> instrs;
    private TempFactory temps;

    public IRVisitor() {
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
        return null;
    }

    public Temp visit(FunctionBody f) {
        return null;
    }

    public Temp visit(FunctionCall f) {
        return null;
    }

    public Temp visit(FunctionDecl f) {
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
        return null;
    }

    public Temp visit(WhileStatement s) {
        return null;
    }

    public IRProgram getIRProgram() {
            return irProgram;
    }
}
