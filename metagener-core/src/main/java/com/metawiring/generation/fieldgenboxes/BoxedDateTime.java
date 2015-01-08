package com.metawiring.generation.fieldgenboxes;

import com.metawiring.types.functiontypes.TypedFieldFunction;
import org.joda.time.DateTime;

public class BoxedDateTime implements TypedFieldFunction<DateTime> {
    @Override
    public DateTime apply(long value) {
        return new DateTime(value);
    }
}
