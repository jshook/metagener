package com.metawiring.generation.fieldgenericfuncs;

import com.metawiring.annotations.Input;
import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.GenericFieldFunction;

@Input({String.class})
@Output({String.class})
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
