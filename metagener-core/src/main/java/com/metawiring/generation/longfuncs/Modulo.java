package com.metawiring.generation.longfuncs;

import com.metawiring.types.functiontypes.LongFieldFunction;

/**
 * Simple modulo by a number.
 */
public class Modulo implements LongFieldFunction {

    private final long modulo;

    public Modulo(long modulo) {
        this.modulo = modulo;
    }

    public Modulo(String modulo) {
        this(Long.valueOf(modulo));
    }

    public Long apply(Long aLong) {
        return aLong % modulo;
    }

    @Override
    public long applyAsLong(long operand) {
        long result = operand & modulo;
        return result;
    }

}
