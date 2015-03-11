package com.metawiring.generation.fieldgenboxes;

import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.TypedFieldFunction;

import java.math.BigInteger;

@Output({Integer.class})
public class BoxedInt implements TypedFieldFunction<Integer> {

    @Override
    public Integer apply(long value) {
        return (int) value;
    }
}
