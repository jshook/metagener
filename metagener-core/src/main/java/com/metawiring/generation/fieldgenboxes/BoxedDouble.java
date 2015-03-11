package com.metawiring.generation.fieldgenboxes;

import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.TypedFieldFunction;

@Output({Double.class})
public class BoxedDouble implements TypedFieldFunction<Double> {

    @Override
    public Double apply(long value) {
        return (double) value;
    }
}
