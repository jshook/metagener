package com.metawiring.generation.fieldgenboxes;

import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.TypedFieldFunction;

@Output(String.class)
public class BoxedString implements TypedFieldFunction<String> {

    @Override
    public String apply(long value) {
        return String.valueOf(value);
    }
}
