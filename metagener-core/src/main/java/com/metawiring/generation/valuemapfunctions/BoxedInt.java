package com.metawiring.generation.valuemapfunctions;

import com.metawiring.generation.FieldFunction;

public class BoxedInt implements FieldFunction<Long,Integer> {
    @Override
    public Integer apply(Long aLong) {
        return (int) aLong.longValue();
    }
}
