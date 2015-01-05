package com.metawiring.generation;

public class TextFieldFunction implements FieldFunction<Long,String> {

    @Override
    public String apply(Long aLong) {
        return String.valueOf(aLong);
    }
}
