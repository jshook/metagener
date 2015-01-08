package com.metawiring.generation.fieldgenericfuncs;

import com.metawiring.types.functiontypes.GenericFieldFunction;

public class Prefix implements GenericFieldFunction<String, String> {

    private String prefix;

    public Prefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String apply(String s) {
        return prefix + s;
    }
}
