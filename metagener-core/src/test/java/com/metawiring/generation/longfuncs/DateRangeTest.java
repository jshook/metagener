package com.metawiring.generation.longfuncs;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.*;

public class DateRangeTest {

    @Test
    public void testApplyAsLong() throws Exception {
        DateRange dr = new DateRange("2000","2100");
        for (long i=0;i<100;i++) {
            long input = (long)(0.0101d * (double) i * Long.MAX_VALUE);
            long dateRange01 = dr.applyAsLong(input);
            DateTime dt = new DateTime(dateRange01+1, DateTimeZone.UTC);
            assertThat((long) dt.getYear(), is(2000+i));
        }
    }
}