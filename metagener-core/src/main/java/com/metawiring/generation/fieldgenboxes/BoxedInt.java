package com.metawiring.generation.fieldgenboxes;

import com.metawiring.types.functiontypes.TypedFieldFunction;

public class BoxedInt implements TypedFieldFunction<Integer> {

    @Override
    public Integer apply(long value) {
        return (int) value;
    }
}
