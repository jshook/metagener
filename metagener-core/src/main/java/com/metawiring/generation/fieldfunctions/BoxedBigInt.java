package com.metawiring.generation.fieldfunctions;

import com.metawiring.generation.FieldFunction;

import java.math.BigInteger;

public class BoxedBigInt implements FieldFunction<BigInteger> {
    @Override
    public BigInteger apply(long value) {
        return BigInteger.valueOf(value);
    }
}
