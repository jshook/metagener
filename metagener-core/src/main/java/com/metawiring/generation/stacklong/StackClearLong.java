package com.metawiring.generation.stacklong;

import com.metawiring.types.functiontypes.LongStackAware;
import com.metawiring.types.functiontypes.LongUnaryFieldFunction;

public class StackClearLong implements LongUnaryFieldFunction, LongStackAware {

    private long[] stack;

    @Override
    public void applyLongStack(long[] stack) {
        this.stack = stack;
    }

    @Override
    public long applyAsLong(long operand) {
        stack[0]=0;
        return operand;
    }
}
