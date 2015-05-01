package com.metawiring.generation.fieldgenericfuncs;

import com.metawiring.annotations.Input;
import com.metawiring.annotations.Output;
import com.metawiring.generation.core.DateTimeUtil;
import com.metawiring.types.functiontypes.GenericFieldFunction;
import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 * Subtract the specified period from the DateTime. The duration used is calculated as a standard duration,
 * assuming a 7 day week, 24 hour day, 60 minute hour and 60 second minute. (Expect this to be inconsistent for
 * leap seconds, etc)
 */
@Input(DateTime.class)
@Output(DateTime.class)
public class DateMinus implements GenericFieldFunction<DateTime,DateTime> {
    private Duration duration;

    public DateMinus(Duration duration) {
        this.duration = duration;
    }
    public DateMinus(String periodSpec) {
        this.duration = DateTimeUtil.periodFormatter.parsePeriod(periodSpec).toStandardDuration();
    }

    @Override
    public DateTime apply(DateTime dateTime) {
        return dateTime.minus(duration);
    }
}
