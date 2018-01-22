package AST;

import Types.*;

public interface Visitor {
    public void visit(AssignStatement s);

	public void visit(ArrayAssignStatement s);

	public void visit(ArrayReference a);

	public void visit(Block b);

	public void visit(BooleanLiteral b);

	public void visit(CharacterLiteral c);

	public void visit(ExpressionStatement e);

	public void visit(FloatLiteral f);

	public void visit(FormalParameter p);

	public void visit(Function f);

	public void visit(FunctionBody f);

	public void visit(FunctionCall f);

	public void visit(Identifier i);

	// public void visit(IdentifierValue v);

	public void visit(IfStatement i);

	public void visit(IntegerLiteral i);

    public void visit(OperatorExpression e);

	public void visit(ParenExpression p);

	public void visit(PrintStatement s);

	public void visit(Program p);

	public void visit(ReturnStatement s);

	public void visit(StringLiteral s);

	public void visit(Type t);

	public void visit(VariableDeclaration v);

	public void visit(WhileStatement s);
}
