package com.metawiring.generation.stacklong;

import com.metawiring.types.functiontypes.LongStackAware;
import com.metawiring.types.functiontypes.LongUnaryFieldFunction;

public class StackSwapLong implements LongUnaryFieldFunction, LongStackAware {

    private long[] stack;

    @Override
    public void applyLongStack(long[] stack) {
        this.stack = stack;
    }

    @Override
    public long applyAsLong(long operand) {
        long swap = stack[(int)stack[0]-1];
        stack[(int)stack[0]-1] = stack[(int)stack[0]];
        stack[(int)stack[0]] = swap;
        return swap;
    }
}
