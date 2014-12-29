package com.metawiring.generation.fieldfunctions;

import com.metawiring.generation.FieldFunction;

public class BoxedInt implements FieldFunction<Integer> {
    @Override
    public Integer apply(long value) {
        return (int) value;
    }
}
