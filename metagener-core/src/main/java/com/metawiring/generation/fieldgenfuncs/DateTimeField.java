package com.metawiring.generation.fieldgenfuncs;

import com.metawiring.types.functiontypes.TypedFieldFunction;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeField implements TypedFieldFunction<String> {

    private DateTimeFormatter dateTimeFormatter;

    public DateTimeField(String format) {
        this.dateTimeFormatter = DateTimeFormat.forPattern(format);
    }

    @Override
    public String apply(long value) {
        return dateTimeFormatter.print(value);
    }
}
