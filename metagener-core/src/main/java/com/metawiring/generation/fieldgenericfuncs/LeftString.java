package com.metawiring.generation.fieldgenericfuncs;

import com.metawiring.types.functiontypes.GenericFieldFunction;

public class LeftString implements GenericFieldFunction<String, String> {

    private int prefixLength;

    public LeftString(int prefixLength) {
        this.prefixLength = prefixLength;
    }

    public LeftString(String prefixLength) {
        this(Integer.valueOf(prefixLength));
    }

    @Override
    public String apply(String s) {
        return s.substring(0,prefixLength);
    }
}
