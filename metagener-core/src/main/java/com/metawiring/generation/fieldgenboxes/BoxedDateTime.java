package com.metawiring.generation.fieldgenboxes;

import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.TypedFieldFunction;
import org.joda.time.DateTime;

import java.math.BigInteger;

@Output({DateTime.class})
public class BoxedDateTime implements TypedFieldFunction<DateTime> {
    @Override
    public DateTime apply(long value) {
        return new DateTime(value);
    }
}
