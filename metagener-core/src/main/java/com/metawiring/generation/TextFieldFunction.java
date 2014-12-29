package com.metawiring.generation;

public class TextFieldFunction implements FieldFunction<String> {

    @Override
    public String apply(long value) {
        return String.valueOf(value);
    }
}
