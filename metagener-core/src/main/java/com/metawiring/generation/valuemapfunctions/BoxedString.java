package com.metawiring.generation.valuemapfunctions;

import com.metawiring.generation.FieldFunction;

public class BoxedString implements FieldFunction<Long,String> {
    @Override
    public String apply(Long aLong) {
        return String.valueOf(aLong);
    }
}
