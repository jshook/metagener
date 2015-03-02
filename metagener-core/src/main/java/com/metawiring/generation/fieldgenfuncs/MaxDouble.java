package com.metawiring.generation.fieldgenfuncs;

import com.metawiring.types.functiontypes.GenericFieldFunction;

public class MaxDouble implements GenericFieldFunction<Double,Double> {

    private double maxValue;

    public MaxDouble(String maxValue) {
        this.maxValue = Double.valueOf(maxValue);
    }

    public MaxDouble(double maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public Double apply(Double aDouble) {
        return Math.min(aDouble, maxValue);
    }
}
