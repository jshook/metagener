package com.metawiring.generation.fieldgenericfuncs;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.*;

public class DateTimeHoursTest {

    @Test
    public void testScaling() {
        DateTimeHours dth = new DateTimeHours("8:00","17:00");

        DateTime one = new DateTime(2015,1,1,1,1,1,1,DateTimeZone.UTC);
        assertThat(dth.apply(one)).isEqualTo(new DateTime(2015,1,1,8,22,52,875,DateTimeZone.UTC));

        DateTime five = new DateTime(2015,5,5,5,5,5,5,DateTimeZone.UTC);
        assertThat(dth.apply(five)).isEqualTo(new DateTime(2015,5,5,9,54,24,376,DateTimeZone.UTC));

        DateTime eleven = new DateTime(2015,11,11,11,11,11,11,DateTimeZone.UTC);
        assertThat(dth.apply(eleven)).isEqualTo(new DateTime(2015,11,11,12,11,41,629,DateTimeZone.UTC));
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testMinGEMaxException() {
        DateTimeHours dth = new DateTimeHours("18:00","17:00");
    }

}