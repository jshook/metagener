package com.metawiring.generation.fieldgenboxes;

import com.metawiring.types.functiontypes.TypedFieldFunction;

import java.math.BigInteger;

public class BoxedBigInt implements TypedFieldFunction<BigInteger> {
    @Override
    public BigInteger apply(long value) {
        return BigInteger.valueOf(value);
    }
}
