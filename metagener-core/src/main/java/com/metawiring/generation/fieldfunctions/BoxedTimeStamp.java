package com.metawiring.generation.fieldfunctions;

import com.metawiring.generation.FieldFunction;
import org.joda.time.DateTime;

import java.time.LocalDateTime;

public class BoxedTimeStamp implements FieldFunction<DateTime> {
    @Override
    public DateTime apply(long value) {
        return new DateTime(value);
    }
}
