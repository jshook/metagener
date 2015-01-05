package com.metawiring.generation.valuemapfunctions;

import com.metawiring.annotations.FieldFunctionSignature;
import com.metawiring.generation.FieldFunction;

@FieldFunctionSignature(input=String.class,output=String.class)
public class Suffix implements FieldFunction<String,String> {

    private String suffix;

    public Suffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public String apply(String s) {
        return s + suffix;
    }
}
