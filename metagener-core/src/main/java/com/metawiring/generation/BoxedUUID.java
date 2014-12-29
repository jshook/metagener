package com.metawiring.generation;

import java.util.UUID;

public class BoxedUUID implements FieldFunction<UUID> {
    @Override
    public UUID apply(long value) {
        return new UUID(value,value);
    }
}
