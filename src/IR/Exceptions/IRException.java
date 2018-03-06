package IR.Exceptions;

public class IRException extends RuntimeException {
    public IRException(String message) {
        super("\nIR Error: " + message + "\n");
    }
}
