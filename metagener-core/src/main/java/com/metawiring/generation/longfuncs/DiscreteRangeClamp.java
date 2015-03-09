package com.metawiring.generation.longfuncs;

import com.metawiring.types.functiontypes.LongUnaryFieldFunction;

public class DiscreteRangeClamp implements LongUnaryFieldFunction {
    private long minValue;
    private long maxValue;

    public DiscreteRangeClamp(long minValue, long maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public DiscreteRangeClamp(String minValue, String maxValue) {
        this(Long.valueOf(minValue),Long.valueOf(maxValue));
    }


    @Override
    public long applyAsLong(long operand) {
        return Math.max(minValue,Math.min(operand,maxValue));
    }
}
