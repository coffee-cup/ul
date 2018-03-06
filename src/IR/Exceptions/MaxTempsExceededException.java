package IR.Exceptions;

public class MaxTempsExceededException extends IRException {
    public MaxTempsExceededException() {
        super("Maximum number of temporaries has been exceed");
    }
}
