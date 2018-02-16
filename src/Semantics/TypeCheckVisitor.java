package Semantics;

import Semantics.Exceptions.*;
import AST.*;
import Types.*;

public class TypeCheckVisitor implements Visitor<Type> {
    private Environment<FunctionDecl, FunctionDecl> ftable;
    private Environment<Identifier, Type> vtable;

    public TypeCheckVisitor() {
        ftable = new HashEnvironment<FunctionDecl, FunctionDecl>();
        vtable = new HashEnvironment<Identifier, Type>();
    }

    public Type visit(AssignStatement s) {
        return null;
    }

    public Type visit(ArrayAssignStatement s) {
        return null;
    }

    public Type visit(ArrayReference a) {
        return null;
    }

    public Type visit(Block b) {
        return null;
    }

    public Type visit(BooleanLiteral b) {
        return null;
    }

    public Type visit(CharacterLiteral c) {
        return null;
    }

    public Type visit(ExpressionStatement e) {
        return null;
    }

    public Type visit(FloatLiteral f) {
        return null;
    }

    public Type visit(FormalParameter p) {
        return null;
    }

    public Type visit(Function f) {
        vtable.beginScope();
        f.getDecl().accept(this);
        f.getBody().accept(this);
        vtable.endScope();

        return null;
    }

    public Type visit(FunctionBody f) {
        return null;
    }

    public Type visit(FunctionCall f) {
        return null;
    }

    public Type visit(FunctionDecl decl) {
        for (FormalParameter p: decl.getParams()) {
            if (vtable.inCurrentScope(p.getIdent())) {
                throw new MultipleDefinitionException(p);
            }
            vtable.add(p.getIdent(), p.getType().getType());

            if (p.getType().getType() instanceof VoidType) {
                throw new InvalidTypeException(p);
            }
        }

        return null;
    }

    public Type visit(Identifier i) {
        return null;
    }

    public Type visit(IfStatement i) {
        return null;
    }

    public Type visit(IntegerLiteral i) {
        return null;
    }

    public Type visit(OperatorExpression e) {
        return null;
    }

    public Type visit(ParenExpression p) {
        return null;
    }

    public Type visit(PrintStatement s) {
        return null;
    }

    public Type visit(Program p) {
        // Add all function declarations to the ftable
        ftable.beginScope();

        boolean foundMain = false;
        for (Function f: p.getFunctions()) {
            FunctionDecl prevDecl = ftable.lookup(f.getDecl());
            if (prevDecl != null) {
                throw new MultipleDefinitionException(prevDecl, f.getDecl());
            }
            ftable.add(f.getDecl(), f.getDecl());

            if (f.getDecl().getType().getType() instanceof VoidType
                && f.getDecl().getIdent().getName().equals("main")
                && f.getDecl().getParams().size() == 0) {
                foundMain = true;
            }
        }

        if (!foundMain) {
            throw new SemanticException("function void main() not found in program");
        }

        // Visit all functions
        for (Function f: p.getFunctions()) {
            f.accept(this);
        }

        ftable.endScope();
        return null;
    }

    public Type visit(ReturnStatement s) {
        return null;
    }

    public Type visit(StringLiteral s) {
        return null;
    }

    public Type visit(TypeNode t) {
        return null;
    }

    public Type visit(VariableDeclaration v) {
        return null;
    }

    public Type visit(WhileStatement s) {
        return null;
    }
}