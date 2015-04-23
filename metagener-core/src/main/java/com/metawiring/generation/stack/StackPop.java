package com.metawiring.generation.stack;

import com.metawiring.types.functiontypes.LongStackAware;
import com.metawiring.types.functiontypes.LongUnaryFieldFunction;

public class StackPop implements LongUnaryFieldFunction, LongStackAware {

    private long[] stack;

    @Override
    public void applyLongStack(long[] stack) {
        this.stack = stack;
    }

    @Override
    public long applyAsLong(long operand) {
        long value=stack[(int)stack[0]];
        stack[0]--;
        return value;
    }
}
