package com.metawiring.generation.valuemapfunctions;

import com.metawiring.annotations.FieldFunctionSignature;
import com.metawiring.generation.FieldFunction;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@FieldFunctionSignature(input=Long.class,output=String.class)
public class DateTimeField implements FieldFunction<Long,String> {

    private DateTimeFormatter dateTimeFormatter;

    public DateTimeField(String format) {
        this.dateTimeFormatter = DateTimeFormat.forPattern(format);
    }

    @Override
    public String apply(Long aLong) {
        return dateTimeFormatter.print(aLong);
    }
}
