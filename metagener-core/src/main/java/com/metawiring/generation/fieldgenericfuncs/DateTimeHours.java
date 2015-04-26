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
@Output(DateTime.class)
public class DateTimeHours implements GenericFieldFunction<DateTime,DateTime> {

    private String minTimeString = "8:00";
    private String maxTimeString = "17:00";
    private long minDayMillis = 0;
    private long targetSize;
    private static double millisPerDay=Duration.ofDays(1).toMillis();

    private final static DateTimeFormatter[] formatters = new DateTimeFormatter[]{
            DateTimeFormat.forPattern("HH:mm").withZoneUTC(),
            DateTimeFormat.forPattern("HH:mm").withZoneUTC(),
            DateTimeFormat.forPattern("HHmm").withZoneUTC()
    };

    public DateTimeHours(String min, String max) {
        this.minTimeString = min;
        this.maxTimeString = max;
        calculateScale();
    }

    private void calculateScale() {
        minDayMillis = parsedEpochTime(minTimeString);
        long untilMillis= parsedEpochTime(maxTimeString);
        if (untilMillis < minDayMillis) {
            throw new RuntimeException("min daytime must be before max daytime");
        }
        targetSize = untilMillis - minDayMillis;
    }

    private long parsedEpochTime(String timeString) {
        List<Exception> exceptions = new ArrayList<>();
        for (DateTimeFormatter dtf : formatters) {
            try {
                long parsed = dtf.parseMillis(timeString);
                return parsed;
            } catch (Exception e) {
                exceptions.add(e);
            }
        }
        String message="";
        for (Exception e: exceptions) {
            message += e.getMessage() + "\n";
        }
        throw new RuntimeException("Unable to parse [" + timeString + "] with any of the parsers. exceptions:" + message);
    }

    @Override
    public DateTime apply(DateTime dateTime) {

        double domainTime = ((double)dateTime.getMillisOfDay())/millisPerDay;
        long dayBase = dateTime.withTimeAtStartOfDay().getMillis();
        double dayPartial = domainTime * targetSize;
        DateTime mapped = new DateTime(dayBase + minDayMillis + (long)dayPartial,DateTimeZone.UTC);
        return mapped;
    }

}
