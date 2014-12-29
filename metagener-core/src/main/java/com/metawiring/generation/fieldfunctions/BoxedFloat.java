package com.metawiring.generation.fieldfunctions;

import com.metawiring.generation.FieldFunction;

public class BoxedFloat implements FieldFunction<Float> {
    @Override
    public Float apply(long value) {
        return (float) value;
    }
}
