package com.metawiring.generation.longfuncs;

import com.metawiring.types.functiontypes.LongFieldFunction;

public class Multiply implements LongFieldFunction {

    private long factor=1l;
    public Multiply(String factor) {
        this.factor = Long.valueOf(factor);

    }

    @Override
    public long applyAsLong(long operand) {
        return factor*operand;
    }
}
