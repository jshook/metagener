package com.metawiring.generation.fieldgenfuncs;

import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.TypedFieldFunction;
import uk.ydubey.formatter.numtoword.NumberInWordsFormatter;

@Output({String.class})
public class NamedNumberString implements TypedFieldFunction<String> {

    private final NumberInWordsFormatter formatter = NumberInWordsFormatter.getInstance();

    @Override
    public String apply(long value) {

        if (value == 0l) {
            return "zero";
        }

        String result = formatter.format((int) value);
        return result;
    }
}
