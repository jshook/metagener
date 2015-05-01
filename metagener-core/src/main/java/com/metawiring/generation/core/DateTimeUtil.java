package com.metawiring.generation.core;

import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public class DateTimeUtil {
    public static PeriodFormatter periodFormatter = new PeriodFormatterBuilder()

            .appendWeeks().appendSuffix("w").minimumPrintedDigits(0)
            .appendDays().appendSuffix("d").minimumPrintedDigits(0)
            .appendHours().appendSuffix("H").minimumPrintedDigits(0)
            .appendMinutes().appendSuffix("M").minimumPrintedDigits(0)
            .appendSeconds().appendSuffix("S").minimumPrintedDigits(0)
            .appendMillis3Digit().appendSuffix("ms").minimumPrintedDigits(0)
            .toFormatter();

}
