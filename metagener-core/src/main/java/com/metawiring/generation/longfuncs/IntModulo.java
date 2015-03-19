package com.metawiring.generation.longfuncs;

import com.metawiring.types.functiontypes.LongUnaryFieldFunction;

public class IntModulo implements LongUnaryFieldFunction {

    @Override
    public long applyAsLong(long operand) {
        return operand % Integer.MAX_VALUE;
    }
}
