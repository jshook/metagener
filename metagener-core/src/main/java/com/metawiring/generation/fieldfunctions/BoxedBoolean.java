package com.metawiring.generation.fieldfunctions;

import com.metawiring.generation.FieldFunction;

public class BoxedBoolean implements FieldFunction<Boolean> {
    @Override
    public Boolean apply(long value) {
        return ((value % 2)==0);
    }
}
