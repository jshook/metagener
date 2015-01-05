package com.metawiring.generation.valuemapfunctions;

import com.metawiring.generation.FieldFunction;
import org.joda.time.DateTime;

public class BoxedTimeStamp implements FieldFunction<Long,DateTime> {

    @Override
    public DateTime apply(Long aLong) {
        return new DateTime(aLong);
    }
}
