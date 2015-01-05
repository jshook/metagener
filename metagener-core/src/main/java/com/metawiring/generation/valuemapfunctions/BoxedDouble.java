package com.metawiring.generation.valuemapfunctions;

import com.metawiring.generation.FieldFunction;

public class BoxedDouble implements FieldFunction<Long,Double> {

    @Override
    public Double apply(Long aLong) {
        return Double.valueOf(aLong);
    }
}
