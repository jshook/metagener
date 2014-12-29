package com.metawiring.generation.fieldfunctions;

import com.metawiring.generation.FieldFunction;
import uk.ydubey.formatter.numtoword.NumberInWordsFormatter;

public class NamedNumberString implements FieldFunction<String> {

    private final NumberInWordsFormatter formatter = NumberInWordsFormatter.getInstance();

    @Override
    public String apply(long value) {
        String result = formatter.format((int) value);
        return result;
    }
}
