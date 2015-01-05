package com.metawiring.generation.valuemapfunctions;

import com.metawiring.generation.FieldFunction;

public class BoxedFloat implements FieldFunction<Long,Float> {
    @Override
    public Float apply(Long aLong) {
        return Float.valueOf(aLong);
    }
}
