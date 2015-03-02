package com.metawiring.generation.longfuncs;

import com.metawiring.types.functiontypes.LongUnaryFieldFunction;

public class LongModulo implements LongUnaryFieldFunction {
    private long modulo;

    public LongModulo(long modulo) {
        this.modulo = modulo;
    }

    public LongModulo(String modulo) {
        this(Long.valueOf(modulo));
    }

    @Override
    public long applyAsLong(long operand) {
        return operand % modulo;
    }
}
