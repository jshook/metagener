package com.metawiring.generation.fieldgenboxes;

import com.metawiring.types.functiontypes.TypedFieldFunction;

public class BoxedFloat implements TypedFieldFunction<Float> {
    @Override
    public Float apply(long value) {
        return (float) value;
    }
}
