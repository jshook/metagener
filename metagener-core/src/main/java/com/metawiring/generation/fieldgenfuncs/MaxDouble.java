package com.metawiring.generation.fieldgenfuncs;

import com.metawiring.annotations.Input;
import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.GenericFieldFunction;

@Input({Double.class})
@Output({Double.class})
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
