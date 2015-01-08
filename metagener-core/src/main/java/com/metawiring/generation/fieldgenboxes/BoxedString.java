package com.metawiring.generation.fieldgenboxes;

import com.metawiring.types.FieldFunctionSignature;
import com.metawiring.types.functiontypes.TypedFieldFunction;

@FieldFunctionSignature(input=Long.class,output=String.class)
public class BoxedString implements TypedFieldFunction<String> {

    @Override
    public String apply(long value) {
        return String.valueOf(value);
    }
}
