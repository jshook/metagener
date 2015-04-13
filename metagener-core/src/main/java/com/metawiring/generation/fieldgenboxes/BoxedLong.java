package com.metawiring.generation.fieldgenboxes;

import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.TypedFieldFunction;

@Output({Long.class})
public class BoxedLong implements TypedFieldFunction<Long> {

    @Override
    public Long apply(long value) {
        return value;
    }
}
