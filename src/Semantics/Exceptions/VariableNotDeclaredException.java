package Semantics.Exceptions;

import AST.*;
import Types.*;

public class VariableNotDeclaredException extends SemanticException {
    public VariableNotDeclaredException(Identifier i) {
        super("variable " + i.getName() + " not declared", i);
    }
}
