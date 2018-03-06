package IR;

public enum TempClass {
    UNKNOWN,
    PARAMETER,
    LOCAL,
    TEMP;

    @Override
    public String toString() {
        switch(this) {
        case UNKNOWN: return "UNKNOWN";
        case PARAMETER: return "P";
        case LOCAL: return "L";
        case TEMP: return "T";
        }
		return null;
    }
}
