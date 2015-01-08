package com.metawiring.generation.fieldgenboxes;

import com.metawiring.types.FieldFunctionSignature;
import com.metawiring.types.functiontypes.TypedFieldFunction;

@FieldFunctionSignature(input=Long.class,output=Double.class)
public class BoxedDouble implements TypedFieldFunction<Double> {

    @Override
    public Double apply(long value) {
        return (double) value;
    }
}
