package Semantics;

import java.util.ArrayList;
import Semantics.Exceptions.*;
import AST.*;
import Types.*;

public class TypeCheckVisitor implements Visitor<Type> {
    private Environment<String, FunctionDecl> ftable;
    private Environment<Identifier, Type> vtable;

    public TypeCheckVisitor() {
        ftable = new HashEnvironment<String, FunctionDecl>();
        vtable = new HashEnvironment<Identifier, Type>();
    }

    public Type visit(AssignStatement s) {
        Type t1 = vtable.lookup(s.getName());
        if (t1 == null) {
            throw new NotDeclaredException(s.getName());
        }

        Type t2 = s.getExpr().accept(this);
        if (!t1.equals(t2) && !(FloatType.check(t1) && IntegerType.check(t2))) {
            throw new TypeMismatchException(s, t1, t2);
        }
        return null;
    }

    public Type visit(ArrayAssignStatement s) {
        return null;
    }

    public Type visit(ArrayReference a) {
        return null;
    }

    public Type visit(Block b) {
        for (Statement s: b.getStmts()) {
            s.accept(this);
        }

        return null;
    }

    public Type visit(BooleanLiteral b) {
        return BooleanType.getInstance();
    }

    public Type visit(CharacterLiteral c) {
        return CharType.getInstance();
    }

    public Type visit(ExpressionStatement e) {
        return e.getExpr().accept(this);
    }

    public Type visit(FloatLiteral f) {
        return FloatType.getInstance();
    }

    public Type visit(FormalParameter p) {
        return p.getType();
    }

    public Type visit(Function f) {
        vtable.beginScope();
        f.getDecl().accept(this);
        f.getBody().accept(this);
        vtable.endScope();

        return null;
    }

    public Type visit(FunctionBody f) {
        for (VariableDeclaration v: f.getVars()) {
            if (vtable.inCurrentScope(v.getIdent())) {
                throw new MultipleDefinitionException(v);
            }
            vtable.add(v.getIdent(), v.accept(this));

            if (VoidType.check(v.accept(this))) {
                throw new InvalidTypeException(v);
            }
        }

        for (Statement s: f.getStmts()) {
            s.accept(this);
        }

        return null;
    }

    public Type visit(FunctionCall f) {
        ArrayList<Type> paramTypes = new ArrayList<Type>();
        for (Expression e: f.getParams()) {
            paramTypes.add(e.accept(this));
        }

        String callString = f.getCallString(paramTypes);
        FunctionDecl decl = ftable.lookup(callString);
        if (decl == null) {
            throw new NotDeclaredException(callString, f.getName());
        }

        return decl.getType();
    }

    public Type visit(FunctionDecl decl) {
        for (FormalParameter p: decl.getParams()) {
            if (vtable.inCurrentScope(p.getIdent())) {
                throw new MultipleDefinitionException(p);
            }
            vtable.add(p.getIdent(), p.accept(this));

            if (VoidType.check(p.getType())) {
                throw new InvalidTypeException(p);
            }
        }

        return null;
    }

    public Type visit(Identifier i) {
        Type t = vtable.lookup(i);
        if (t == null) {
            throw new NotDeclaredException(i);
        }
        return t;
    }

    public Type visit(IfStatement i) {
        return null;
    }

    public Type visit(IntegerLiteral i) {
        return IntegerType.getInstance();
    }

    public Type visit(OperatorExpression e) {
        return null;
    }

    public Type visit(ParenExpression p) {
        return p.getExpr().accept(this);
    }

    public Type visit(PrintStatement s) {
        return null;
    }

    public Type visit(Program p) {
        // Add all function declarations to the ftable
        ftable.beginScope();

        boolean foundMain = false;
        for (Function f: p.getFunctions()) {
            FunctionDecl prevDecl = ftable.lookup(f.getDecl().toString());
            if (prevDecl != null) {
                throw new MultipleDefinitionException(prevDecl, f.getDecl());
            }
            ftable.add(f.getDecl().toString(), f.getDecl());

            if (VoidType.check(f.getDecl().getType())
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
        return StringType.getInstance();
    }

    public Type visit(TypeNode t) {
        return t.getType();
    }

    public Type visit(VariableDeclaration v) {
        return v.getType();
    }

    public Type visit(WhileStatement s) {
        return null;
    }
}
