package com.metawiring.generation.fieldgenboxes;

import com.metawiring.types.functiontypes.TypedFieldFunction;

public class BoxedBoolean implements TypedFieldFunction<Boolean> {
    @Override
    public Boolean apply(long value) {
        return (value % 2) == 0;
    }
}
