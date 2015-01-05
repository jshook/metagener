package com.metawiring.generation.valuemapfunctions;

import com.metawiring.annotations.FieldFunctionSignature;
import com.metawiring.generation.FieldFunction;

@FieldFunctionSignature(input=String.class,output=String.class)
public class Prefix implements FieldFunction<String,String> {

    private String prefix;

    public Prefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String apply(String s) {
        return prefix + s;
    }
}
