package com.metawiring.generation.valuemapfunctions;

import com.metawiring.generation.FieldFunction;

import java.util.UUID;

public class BoxedUUID implements FieldFunction<Long,UUID> {
    @Override
    public UUID apply(Long aLong) {
        return new UUID(aLong,aLong);
    }
}
