/*
*   Copyright 2015 jshook
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*/
package com.metawiring.generation.longfuncs;

import com.metawiring.types.functiontypes.LongFieldFunction;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.ArrayList;
import java.util.List;

/**
 * Map a long value to a range. The input is mapped from positive long number line to the
 * specified range, based on the parsed dates. The most specific dates are tried first,
 * starting from yyyyMMdd'T'HHmmssZ, all the way to just yyyy. (This is only parsed at
 * setup time) All timezones are UTC
 */
public class DateRange implements LongFieldFunction {

    private long min;
    private long max;
    private String minSpec;
    private String maxSpec;
    private double scaleFactor;

    private final static DateTimeFormatter[] formatters = new DateTimeFormatter[]{
            ISODateTimeFormat.basicDateTimeNoMillis().withZoneUTC(), // yyyyMMdd'T'HHmmssZ
            ISODateTimeFormat.basicDate().withZoneUTC(), // yyyyMMdd'T'HHmmssZ
            ISODateTimeFormat.date().withZoneUTC(), // yyyy-MM-dd
            DateTimeFormat.forPattern("yyyyMM").withZoneUTC(),
            DateTimeFormat.forPattern("yyyy").withZoneUTC()
    };

    public DateRange(String minSpec, String maxSpec) {
        this.minSpec = minSpec;
        this.maxSpec = maxSpec;
        this.min = parsedEpochTime(this.minSpec);
        this.max = parsedEpochTime(this.maxSpec);

        if (this.max <= this.min) {
            throw new RuntimeException("date ranges must be in mix-max order");
        }

        long totalSpan = this.max - this.min;
        scaleFactor = ((double) totalSpan ) /((double) Long.MAX_VALUE);
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

    /**
     * Convert to double precision for the translation and scaling
     * @param operand the input long, representing unix epoch millisecond time
     * @return The millisecond epoch time ranged from the long precision to the specifed range
     */
    @Override
    public long applyAsLong(long operand) {
        long result = min + (long) ( (double) operand * scaleFactor );
        return result;
    }
}
