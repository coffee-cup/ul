package Semantics.Exceptions;

import AST.*;
import Types.*;

public class MultipleDefinitionException extends SemanticException {
    public MultipleDefinitionException(FunctionDecl prevDecl, FunctionDecl decl) {
        super("error: function " + decl.toString() + " previously defined on line " + prevDecl.getIdent().getLine() + "\n" +
              SemanticException.getPositionMessage(decl.getIdent()));
    }
}
