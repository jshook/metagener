package com.metawiring.generation.fieldfunctions;

import com.metawiring.generation.FieldFunction;

public class BoxedDouble implements FieldFunction<Double> {
    @Override
    public Double apply(long value) {
        return (double) value;
    }
}
