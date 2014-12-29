package com.metawiring.generation.fieldfunctions;

import com.metawiring.generation.FieldFunction;

public class BoxedLong implements FieldFunction<Long> {
    @Override
    public Long apply(long value) {
        return value;
    }
}
