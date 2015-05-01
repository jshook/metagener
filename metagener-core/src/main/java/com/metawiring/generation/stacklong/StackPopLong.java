package com.metawiring.generation.stacklong;

import com.metawiring.annotations.Input;
import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.GenericFieldFunction;
import com.metawiring.types.functiontypes.LongStackAware;
import com.metawiring.types.functiontypes.LongUnaryFieldFunction;

@Input(Object.class)
@Output(Long.class)
public class StackPopLong implements GenericFieldFunction<Object,Long>, LongStackAware {

    private long[] stack;

    @Override
    public void applyLongStack(long[] stack) {
        this.stack = stack;
    }

    @Override
    public Long apply(Object o) {
        long value=stack[(int)stack[0]];
        stack[0]--;
        return value;
    }
}
