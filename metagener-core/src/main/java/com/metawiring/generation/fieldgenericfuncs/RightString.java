package com.metawiring.generation.fieldgenericfuncs;

import com.metawiring.annotations.Input;
import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.GenericFieldFunction;

@Input({String.class})
@Output({String.class})
public class RightString implements GenericFieldFunction<String, String> {

    private int suffixLength;

    public RightString(int suffixLength) {
        this.suffixLength = suffixLength;
    }

    public RightString(String suffixLength) {
        this(Integer.valueOf(suffixLength));
    }

    @Override
    public String apply(String s) {
        if (s.length()>=suffixLength) {
            return s;
        }
        return s.substring(s.length()-suffixLength);
    }
}
