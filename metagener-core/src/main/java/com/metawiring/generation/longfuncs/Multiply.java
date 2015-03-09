package com.metawiring.generation.longfuncs;

import com.metawiring.types.functiontypes.LongUnaryFieldFunction;

public class Multiply implements LongUnaryFieldFunction {

    private long factor=1l;
    public Multiply(String factor) {
        this.factor = Long.valueOf(factor);

    }

    @Override
    public long applyAsLong(long operand) {
        return factor*operand;
    }
}
