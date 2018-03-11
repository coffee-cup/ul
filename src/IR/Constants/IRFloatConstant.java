package IR.Constants;

public class IRFloatConstant extends IRConstant {
    private float value;

    public IRFloatConstant(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public String toString() {
        return Float.toString(value);
    }
}
