package com.metawiring.generation.fieldgenericfuncs;

import com.metawiring.annotations.Input;
import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.GenericFieldFunction;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Takes the time of day as a fraction of the whole day, and scales it to the
 * target time range. For example, specifying 10:00 to 14:00 will cause all
 * resulting date times to be within that range (10:00 am till 2:00 pm), based
 * on how far the time would have been between 0:00 and 23:59.
 */
@Input(DateTime.class)
@Output(Long.class)
public class ToMillis implements GenericFieldFunction<DateTime,Long> {

    @Override
    public Long apply(DateTime dateTime) {
        return dateTime.getMillis();
    }
}
