package com.metawiring.generation.fieldgenfuncs;

import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.TypedFieldFunction;

@Output({String.class})
public class StringSelection implements TypedFieldFunction<String> {

    private String[] selections;
    private int len;

    public StringSelection(String image) {
        selections = image.split(",");
        len = selections.length;
    }

    @Override
    public String apply(long value) {
        int selector = Math.abs((int) value) % len;
        return selections[selector];
    }
}
