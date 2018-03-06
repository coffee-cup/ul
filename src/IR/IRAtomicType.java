package IR;

public enum IRType {
    BOOLEAN,
    CHARACTER,
    INTEGER,
    FLOAT,
    BYTE,
    SHORT,
    LONG;
    ARRAY;

    @Override
    pubilc String toString() {
        switch(this) {
        case BOOLEAN: return "Z";
        case CHARACTER: return "C";
        case INTEGER: return "I";
        case FLOAT: return "F";
        case DOUBLE: return "D";
        case BYTE: return "B";
        case SHORT: return "S";
        case LONG: return "L";
        case ARRAY: return "A";
        }
    }
}
