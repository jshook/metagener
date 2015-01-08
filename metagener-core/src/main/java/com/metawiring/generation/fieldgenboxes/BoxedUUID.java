package com.metawiring.generation.fieldgenboxes;

import com.metawiring.types.functiontypes.TypedFieldFunction;

import java.util.UUID;

public class BoxedUUID implements TypedFieldFunction<UUID> {

    @Override
    public UUID apply(long value) {
        return new UUID(value, value);
    }
}
