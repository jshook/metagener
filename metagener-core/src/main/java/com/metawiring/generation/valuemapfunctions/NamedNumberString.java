package com.metawiring.generation.valuemapfunctions;

import com.metawiring.annotations.FieldFunctionSignature;
import com.metawiring.generation.FieldFunction;
import uk.ydubey.formatter.numtoword.NumberInWordsFormatter;

@FieldFunctionSignature(input=Long.class,output=String.class)
public class NamedNumberString implements FieldFunction<Long,String> {

    private final NumberInWordsFormatter formatter = NumberInWordsFormatter.getInstance();

    @Override
    public String apply(Long aLong) {

        if (aLong.equals(0l)) {
            return "zero";
        }

        String result = formatter.format((int) aLong.longValue());
        return result;
    }

}
