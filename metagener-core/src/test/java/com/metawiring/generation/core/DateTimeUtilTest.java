package com.metawiring.generation.core;

import org.joda.time.Period;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DateTimeUtilTest {

    @Test
    public void testStandardPeriodFormatter() {
        Period oneWeek = DateTimeUtil.periodFormatter.parsePeriod("1w");
        assertThat(oneWeek.toStandardDuration().getMillis()).isEqualTo(604800000l);
        Period oneDay = DateTimeUtil.periodFormatter.parsePeriod("1d");
        assertThat(oneDay.toStandardDuration().getMillis()).isEqualTo(86400000l);
        Period oneWeekOneDay = DateTimeUtil.periodFormatter.parsePeriod("1w1d");
        assertThat(oneWeekOneDay.toStandardDuration().getMillis()).isEqualTo(691200000l);

    }

}