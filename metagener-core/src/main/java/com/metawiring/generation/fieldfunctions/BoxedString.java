package com.metawiring.generation.fieldfunctions;

import com.metawiring.generation.FieldFunction;

public class BoxedString implements FieldFunction<String> {
    @Override
    public String apply(long value) {
        return String.valueOf(value);
    }
}
