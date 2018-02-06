package AST;

import Types.*;

public interface Visitor<T> {
    public T visit(AssignStatement s);

    public T visit(ArrayAssignStatement s);

    public T visit(ArrayReference a);

    public T visit(Block b);

    public T visit(BooleanLiteral b);

    public T visit(CharacterLiteral c);

    public T visit(ExpressionStatement e);

    public T visit(FloatLiteral f);

    public T visit(FormalParameter p);

    public T visit(Function f);

    public T visit(FunctionBody f);

    public T visit(FunctionCall f);

    public T visit(Identifier i);

    public T visit(IfStatement i);

    public T visit(IntegerLiteral i);

    public T visit(OperatorExpression e);

    public T visit(ParenExpression p);

    public T visit(PrintStatement s);

    public T visit(Program p);

    public T visit(ReturnStatement s);

    public T visit(StringLiteral s);

    public T visit(TypeNode t);

    public T visit(VariableDeclaration v);

    public T visit(WhileStatement s);
}
