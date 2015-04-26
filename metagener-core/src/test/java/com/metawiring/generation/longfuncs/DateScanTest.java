package com.metawiring.generation.longfuncs;

import com.metawiring.configdefs.MutableEntityDef;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.*;

public class DateScanTest {

    @Test
    public void testApplyEntityDef() throws Exception {
        DateScan ds = new DateScan("2008-05-15","2009-05-15");
        ds.applyEntityDef(new MutableEntityDef().setName("datedummy").setPopulationSize(100));
        long result = 0l;

        result = ds.applyAsLong(0);
        assertThat(new DateTime(result, DateTimeZone.UTC))
                .isEqualTo(new DateTime(2008, 5, 15, 0, 0, 0, 0, DateTimeZone.UTC));

        result = ds.applyAsLong(49);
        assertThat(new DateTime(result, DateTimeZone.UTC))
                .isEqualTo(new DateTime(2008,11,9,20,24,0,0, DateTimeZone.UTC));

        result = ds.applyAsLong(97);
        assertThat(new DateTime(result, DateTimeZone.UTC))
                .isEqualTo(new DateTime(2009, 5, 4, 1, 12, 0, 0, DateTimeZone.UTC));

        result = ds.applyAsLong(100);
        assertThat(new DateTime(result, DateTimeZone.UTC))
                .isEqualTo(new DateTime(2009, 5, 15, 0, 0, 0, 0, DateTimeZone.UTC));

    }
}