package Types;

import Types.*;

public class ArrayType {
    IntegerType count;
    Type[] array;

    public ArrayType(IntegerType count) {
        this.count = count;
        this.array = new Type[count.value];
    }
}
