package com.metawiring.generation.core;

import com.metawiring.types.functiontypes.GenericFieldFunction;

public class TextFieldFunction implements GenericFieldFunction<Long,String> {

    @Override
    public String apply(Long aLong) {
        return String.valueOf(aLong);
    }
}
