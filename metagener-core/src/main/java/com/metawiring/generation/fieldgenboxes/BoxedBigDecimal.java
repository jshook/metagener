package com.metawiring.generation.fieldgenboxes;

import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.TypedFieldFunction;

import java.math.BigDecimal;
import java.math.BigInteger;

@Output({BigDecimal.class})
public class BoxedBigDecimal implements TypedFieldFunction<BigDecimal> {
    @Override
    public BigDecimal apply(long value) {
        return BigDecimal.valueOf(value);
    }
}
