package com.metawiring.generation.fieldgenericfuncs;

import com.metawiring.annotations.Input;
import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.GenericFieldFunction;

@Input({String.class})
@Output({String.class})
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
