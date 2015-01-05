package com.metawiring.generation.valuemapfunctions;

import com.metawiring.generation.FieldFunction;

import java.util.HashMap;
import java.util.Map;

public class BoxedMap implements FieldFunction<Long,Map<Long,Long>> {

    @Override
    public Map<Long, Long> apply(Long aLong) {
        HashMap<Long,Long> longmap = new HashMap<Long,Long>();
        longmap.put(aLong,aLong);
        return longmap;
    }
}
