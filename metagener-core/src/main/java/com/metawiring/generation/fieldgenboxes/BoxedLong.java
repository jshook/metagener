package com.metawiring.generation.fieldgenboxes;

import com.metawiring.types.functiontypes.TypedFieldFunction;

public class BoxedLong implements TypedFieldFunction<Long> {

    @Override
    public Long apply(long value) {
        return value;
    }
}
