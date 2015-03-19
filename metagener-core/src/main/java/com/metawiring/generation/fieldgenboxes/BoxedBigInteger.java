package com.metawiring.generation.fieldgenboxes;

import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.TypedFieldFunction;

import java.math.BigInteger;

@Output({BigInteger.class})
public class BoxedBigInteger implements TypedFieldFunction<BigInteger> {
    @Override
    public BigInteger apply(long value) {
        return BigInteger.valueOf(value);
    }
}
