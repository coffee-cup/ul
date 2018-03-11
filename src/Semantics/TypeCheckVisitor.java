package Semantics;

import java.util.ArrayList;
import java.util.Arrays;
import Semantics.Exceptions.*;
import AST.*;
import Types.*;

public class TypeCheckVisitor implements Visitor<Type> {
    private Environment<String, ArrayList<FunctionDecl>> ftable;
    private Environment<Identifier, Type> vtable;

    private FunctionDecl currentFunction;
    private boolean foundReturn = false;

    public TypeCheckVisitor() {
        ftable = new HashEnvironment<String, ArrayList<FunctionDecl>>();
        vtable = new HashEnvironment<Identifier, Type>();
    }

    public Type visit(AddExpression e) {
        ArrayList<Class<? extends Type>> validTypes =
            new ArrayList<Class<? extends Type>>(Arrays.asList(IntegerType.class,
                                                               FloatType.class,
                                                               CharType.class,
                                                               StringType.class));
        return checkBinaryExpression(validTypes, e, null);
    }

    public Type visit(AssignStatement s) {
        Type tName = s.getName().accept(this);
        Type tExpr = s.getExpr().accept(this);
        if (!(tName.equals(tExpr) || tExpr.isSubtype(tName))) {
            throw new TypeMismatchException(tName, tExpr, s.getExpr());
        }

        return null;
    }

    public Type visit(ArrayAssignStatement s) {
        Type tName = s.getName().accept(this);
        Type tRefExpr = s.getRefExpr().accept(this);
        Type tAssignExpr = s.getAssignExpr().accept(this);

        if (!ArrayType.check(tName)) {
            throw new TypeMismatchException(ArrayType.toTypeString(), tName, s.getName());
        }
        ArrayType tArray = (ArrayType)tName;

        if (!IntegerType.check(tRefExpr)) {
            throw new TypeMismatchException(IntegerType.getInstance(), tRefExpr, s.getRefExpr());
        }

        if (!tAssignExpr.equals(tArray.getArrayOfType())
            && !(FloatType.check(tArray.getArrayOfType()) && IntegerType.check(tAssignExpr))) {
            throw new TypeMismatchException(tArray.getArrayOfType(), tAssignExpr, s.getAssignExpr());
        }

        return null;
    }

    public Type visit(ArrayReference a) {
        Type tName = a.getName().accept(this);
        Type tRefExpr = a.getExpr().accept(this);

        if (!ArrayType.check(tName)) {
            throw new TypeMismatchException(ArrayType.toTypeString(), tName, a);
        }

        if (!IntegerType.check(tRefExpr)) {
            throw new TypeMismatchException(IntegerType.getInstance(), tRefExpr, a.getExpr());
        }

        return ((ArrayType)tName).getArrayOfType();
    }

    public Type visit(Block b) {
        vtable.beginScope();
        for (Statement s: b.getStmts()) {
            s.accept(this);
        }
        vtable.endScope();

        return null;
    }

    public Type visit(BooleanLiteral b) {
        return BooleanType.getInstance();
    }

    public Type visit(CharacterLiteral c) {
        return CharType.getInstance();
    }

    public Type visit(EqualityExpression e) {
        ArrayList<Class<? extends Type>> validTypes =
            new ArrayList<Class<? extends Type>>(Arrays.asList(IntegerType.class,
                                                               FloatType.class,
                                                               CharType.class,
                                                               StringType.class,
                                                               BooleanType.class));
        return checkBinaryExpression(validTypes, e, BooleanType.getInstance());
    }

    public Type visit(ExpressionStatement e) {
        if (e.getExpr() != null) {
            return e.getExpr().accept(this);
        }
        return null;
    }

    public Type visit(FloatLiteral f) {
        return FloatType.getInstance();
    }

    public Type visit(FormalParameter p) {
        Type t = p.getType();

        if (VoidType.check(t)) {
            throw new InvalidTypeException(p);
        }
        if (!checkArrayType(t)) {
            throw new InvalidTypeException((ArrayType)t, p.getTypeNode());
        }

        if (vtable.inCurrentScope(p.getIdent())) {
            throw new MultipleDefinitionException(p);
        }
        vtable.add(p.getIdent(), t);

        return null;
    }

    public Type visit(Function f) {
        FunctionDecl decl = f.getDecl();

        currentFunction = decl;
        foundReturn = false;

        // scope for parameters
        vtable.beginScope();

        decl.accept(this);
        f.getBody().accept(this);

        vtable.endScope();

        // The function should have a return
        if (!VoidType.check(decl.getType()) && !foundReturn) {
            throw new SemanticException("Function " + decl.getIdent().getName()
                                        + " should have a return of type " + decl.getType().toString(), decl.getTypeNode());
        }

        return null;
    }

    public Type visit(FunctionBody f) {
        // Since we cannot shadow parameters, cannot enter the block
        // as it will create a new scope
        for (Statement s: f.getBlock().getStmts()) {
            s.accept(this);
        }

        return null;
    }

    public Type visit(FunctionCall f) {
        ArrayList<Type> paramTypes = new ArrayList<Type>();
        for (Expression e: f.getParams()) {
            paramTypes.add(e.accept(this));
        }

        String functionKey = f.getName().toString();
        ArrayList<FunctionDecl> decls = ftable.lookup(functionKey);
        FunctionDecl foundDecl = null;

        if (decls != null) {
            for (FunctionDecl d: decls) {
                // Check if there are any function decls that can be called
                int status = checkFunctionCall(paramTypes, d);
                if (status == 0) {
                    foundDecl = d;
                } else if (foundDecl == null && status == 1) {
                    foundDecl = d;
                }
            }
        }

        if (foundDecl == null) {
            throw new NotDeclaredException(f.getCallString(paramTypes), f.getName());
        }

        // Tag the function call with the actual declaration that should be called
        f.setDeclCalled(foundDecl);

        return foundDecl.getType();
    }

    public Type visit(FunctionDecl decl) {
        if (!checkArrayType(decl.getType())) {
            throw new InvalidTypeException((ArrayType)decl.getType(), decl.getTypeNode());
        }

        for (FormalParameter p: decl.getParams()) {
            p.accept(this);
        }

        return null;
    }

    public Type visit(Identifier i) {
        return getVariableType(i);
    }

    public Type visit(IfStatement i) {
        Type tCond = i.getExpr().accept(this);
        if (!BooleanType.check(tCond)) {
            throw new TypeMismatchException(BooleanType.getInstance(), tCond, i.getExpr());
        }

        i.getThenBlock().accept(this);
        if (i.getElseBlock() != null) {
            i.getElseBlock().accept(this);
        }

        return null;
    }

    public Type visit(IntegerLiteral i) {
        return IntegerType.getInstance();
    }

    public Type visit(LessThanExpression e) {
        ArrayList<Class<? extends Type>> validTypes =
            new ArrayList<Class<? extends Type>>(Arrays.asList(IntegerType.class,
                                                               FloatType.class,
                                                               CharType.class,
                                                               StringType.class,
                                                               BooleanType.class));
        return checkBinaryExpression(validTypes, e, BooleanType.getInstance());
    }

    public Type visit(MultExpression e) {
        ArrayList<Class<? extends Type>> validTypes =
            new ArrayList<Class<? extends Type>>(Arrays.asList(IntegerType.class,
                                                               FloatType.class));
        return checkBinaryExpression(validTypes, e, null);
    }

    public Type visit(ParenExpression p) {
        return p.getExpr().accept(this);
    }

    public Type visit(PrintStatement s) {
        Type t = s.getExpr().accept(this);
        ArrayList<Class<? extends Type>> validTypes =
            new ArrayList<Class<? extends Type>>(Arrays.asList(IntegerType.class,
                                                               FloatType.class,
                                                               CharType.class,
                                                               StringType.class,
                                                               BooleanType.class));
        if (!checkTypeIs(validTypes, t)) {
            throw new InvalidTypeException(t, s.toString(), s.getExpr());
        }

        return null;
    }

    public Type visit(Program p) {
        // Add all function declarations to the ftable
        ftable.beginScope();

        boolean foundMain = false;
        for (Function f: p.getFunctions()) {
            String functionKey = f.getDecl().getIdent().toString();
            ArrayList<FunctionDecl> decls = ftable.lookup(functionKey);
            if (decls != null) {
                for (FunctionDecl d: decls) {
                    if (d.toString().equals(f.getDecl().toString())) {
                        throw new MultipleDefinitionException(d, f.getDecl());
                    }
                }
            }  else {
                decls = new ArrayList<FunctionDecl>();
                ftable.add(functionKey, decls);
            }
            decls.add(f.getDecl());

            if (VoidType.check(f.getDecl().getType())
                && f.getDecl().getIdent().getName().equals("main")
                && f.getDecl().getParams().size() == 0) {
                foundMain = true;
            }
        }

        if (!foundMain) {
            throw new SemanticException("Function void main() not found in program");
        }

        // Visit all functions
        for (Function f: p.getFunctions()) {
            f.accept(this);
        }

        ftable.endScope();
        return null;
    }

    public Type visit(ReturnStatement s) {
        Expression returnExpr = s.getExpr();

        if (returnExpr == null && !VoidType.check(currentFunction.getType())) {
            throw new ReturnException(currentFunction.getType(), VoidType.getInstance(), s);
        } else if (returnExpr != null) {
            Type tReturn = returnExpr.accept(this);
            if (!currentFunction.getType().equals(tReturn) &&
                !(FloatType.check(currentFunction.getType()) && IntegerType.check(tReturn))) {
                throw new ReturnException(currentFunction.getType(), tReturn, returnExpr);
            }
        }

        foundReturn = true;

        return null;
    }

    public Type visit(StringLiteral s) {
        return StringType.getInstance();
    }

    public Type visit(SubExpression e) {
        ArrayList<Class<? extends Type>> validTypes =
            new ArrayList<Class<? extends Type>>(Arrays.asList(IntegerType.class,
                                                               FloatType.class,
                                                               CharType.class));
        return checkBinaryExpression(validTypes, e, null);
    }

    public Type visit(TypeNode t) {
        return t.getType();
    }

    public Type visit(VariableDeclaration v) {
        Type t = v.getType();

        if (VoidType.check(t)) {
            throw new InvalidTypeException(v);
        }
        if (!checkArrayType(t)) {
            throw new InvalidTypeException((ArrayType)t, v.getTypeNode());
        }

        if (vtable.inCurrentScope(v.getIdent())) {
            throw new MultipleDefinitionException(v);
        }
        vtable.add(v.getIdent(), t);

        return null;
    }

    public Type visit(WhileStatement s) {
        Type tCond = s.getExpr().accept(this);
        if (!BooleanType.check(tCond)) {
            throw new TypeMismatchException(BooleanType.getInstance(), tCond, s.getExpr());
        }

        s.getBlock().accept(this);

        return null;
    }

    private Type getVariableType(Identifier i) {
        Type t = vtable.lookup(i);
        if (t == null) {
            throw new NotDeclaredException(i);
        }
        return t;
    }

    // Check if array of type params is valid for a function decl
    // returns -1 if not valid, 0 if types match exactly, 1 if subtyping is required
    private int checkFunctionCall(ArrayList<Type> params, FunctionDecl d) {
        if (params.size() != d.getParams().size()) {
            return -1;
        }

        boolean subtypingRequired = false;
        for (int i = 0; i < params.size(); i += 1) {
            Type tParam = d.getParams().get(i).getType();

            if (!(params.get(i).equals(tParam) || params.get(i).isSubtype(tParam))) {
                return -1;
            }
            if (params.get(i).isSubtype(tParam)) {
                subtypingRequired = true;
            }
        }

        return subtypingRequired ? 1: 0;
    }

    // Check array is not of type void
    private boolean checkArrayType(Type t) {
        if (!ArrayType.check(t)) {
            return true;
        }

        ArrayType tArr = (ArrayType)t;
        if (VoidType.check(tArr.getArrayOfType())) {
            return false;
        }

        return true;
    }

    private <T extends Type> Type checkBinaryExpression(ArrayList<Class<? extends Type>> validTypes,
                                                           OperatorExpression e,
                                                           Type tReturn) {
        Type tLeft = e.getLeftExpr().accept(this);
        Type tRight = e.getRightExpr().accept(this);

        if (!checkTypeIs(validTypes, tLeft)) {
            throw new InvalidTypeException(tLeft, e.toString(), e.getLeftExpr());
        }

        if (!checkTypeIs(validTypes, tRight)) {
            throw new InvalidTypeException(tRight, e.toString(), e.getRightExpr());
        }

        if (!(tLeft.equals(tRight) || tLeft.isSubtype(tRight) || tRight.isSubtype(tLeft))) {
            throw new TypeMismatchException(tLeft, tRight, e);
        }

        if (tReturn == null) {
            tReturn = Type.greaterType(tLeft, tRight);
        }

        return tReturn;
    }

    private <T extends Type> boolean checkTypeIs(ArrayList<Class<? extends Type>> types, Type t) {
        for (int i = 0; i < types.size(); i += 1) {
            if (t.getClass().equals(types.get(i))) {
                return true;
            }
        }
        return false;
    }
}
