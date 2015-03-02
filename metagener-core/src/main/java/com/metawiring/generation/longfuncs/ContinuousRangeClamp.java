package com.metawiring.generation.longfuncs;

import com.metawiring.types.functiontypes.GenericFieldFunction;

public class ContinuousRangeClamp implements GenericFieldFunction<Double,Double> {
    private double minValue;
    private double maxValue;

    public ContinuousRangeClamp(double minValue, double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public ContinuousRangeClamp(String minValue, String maxValue) {
        this(Double.valueOf(minValue),Double.valueOf(maxValue));
    }


    @Override
    public Double apply(Double aDouble) {
        return Math.max(minValue,Math.min(aDouble,maxValue));
    }
}
