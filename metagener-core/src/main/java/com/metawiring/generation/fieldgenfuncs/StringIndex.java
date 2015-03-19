package com.metawiring.generation.fieldgenfuncs;

import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.TypedFieldFunction;

@Output({String.class})
public class StringIndex implements TypedFieldFunction<String> {

    private String image;
    private int len;

    public StringIndex(String image) {
        this.image = image;
        len = image.length();
    }

    @Override
    public String apply(long value) {
        int offset = Math.abs((int) value) % len;
        return image.substring(offset,offset+1);
    }
}
