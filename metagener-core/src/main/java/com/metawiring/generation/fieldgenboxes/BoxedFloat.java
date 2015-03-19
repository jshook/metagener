package com.metawiring.generation.fieldgenboxes;

import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.TypedFieldFunction;
import java.math.BigInteger;

@Output({Float.class})
public class BoxedFloat implements TypedFieldFunction<Float> {
    @Override
    public Float apply(long value) {
        return (float) value;
    }
}
