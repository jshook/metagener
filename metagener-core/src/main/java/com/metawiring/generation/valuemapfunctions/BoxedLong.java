package com.metawiring.generation.valuemapfunctions;

import com.metawiring.generation.FieldFunction;

public class BoxedLong implements FieldFunction<Long,Long> {

    @Override
    public Long apply(Long aLong) {
        return aLong;
    }
}
