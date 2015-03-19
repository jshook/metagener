package com.metawiring.generation.fieldgenfuncs;

import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.TypedFieldFunction;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@Output({String.class})
public class DateTimeString implements TypedFieldFunction<String> {

    private DateTimeFormatter dateTimeFormatter;

    public DateTimeString(String format) {
        this.dateTimeFormatter = DateTimeFormat.forPattern(format).withZoneUTC();
    }

    @Override
    public String apply(long value) {
        return dateTimeFormatter.print(value);
    }
}
