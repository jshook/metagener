package com.metawiring.generation.stack;

import com.metawiring.types.functiontypes.LongStackAware;
import com.metawiring.types.functiontypes.LongUnaryFieldFunction;

public class StackMultiply implements LongUnaryFieldFunction, LongStackAware {

    private long[] stack;

    @Override
    public void applyLongStack(long[] stack) {
        this.stack = stack;
    }

    @Override
    public long applyAsLong(long operand) {
        stack[(int)stack[0]-1]*=stack[(int)stack[0]];
        stack[0]--;
        return stack[(int)stack[0]];
    }
}
