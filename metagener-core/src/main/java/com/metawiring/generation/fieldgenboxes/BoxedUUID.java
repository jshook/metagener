package com.metawiring.generation.fieldgenboxes;

import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.TypedFieldFunction;

import java.math.BigInteger;
import java.util.UUID;

@Output({UUID.class})
public class BoxedUUID implements TypedFieldFunction<UUID> {

    @Override
    public UUID apply(long value) {
        return new UUID(value, value);
    }
}
