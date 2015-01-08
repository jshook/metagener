package com.metawiring.generation.fieldgenericfuncs;

import com.metawiring.types.FieldFunctionSignature;
import com.metawiring.types.functiontypes.GenericFieldFunction;

@FieldFunctionSignature(input=String.class,output=String.class)
public class Suffix implements GenericFieldFunction<String,String> {

    private String suffix;

    public Suffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public String apply(String s) {
        return s + suffix;
    }
}
