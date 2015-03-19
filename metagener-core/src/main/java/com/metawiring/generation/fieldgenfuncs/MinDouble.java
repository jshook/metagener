package com.metawiring.generation.fieldgenfuncs;

import com.metawiring.annotations.Input;
import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.GenericFieldFunction;

@Input({Double.class})
@Output({Double.class})
public class MinDouble implements GenericFieldFunction<Double,Double> {

    private double minValue;

    public MinDouble(String minValue) {
        this.minValue = Double.valueOf(minValue);
    }

    public MinDouble(double minValue) {
        this.minValue = minValue;
    }

    @Override
    public Double apply(Double aDouble) {
        return Math.max(aDouble,minValue);
    }
}
