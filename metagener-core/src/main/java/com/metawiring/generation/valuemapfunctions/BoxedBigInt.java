package com.metawiring.generation.valuemapfunctions;

import com.metawiring.generation.FieldFunction;

import java.math.BigInteger;

public class BoxedBigInt implements FieldFunction<Long,BigInteger> {
    @Override
    public BigInteger apply(Long value) {
        return BigInteger.valueOf(value);
    }

}
