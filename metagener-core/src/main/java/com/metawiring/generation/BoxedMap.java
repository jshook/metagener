package com.metawiring.generation;

import java.util.HashMap;
import java.util.Map;

public class BoxedMap implements FieldFunction<Map<Long,Long>> {
    @Override
    public Map<Long, Long> apply(long value) {
        HashMap<Long,Long> longmap = new HashMap<Long,Long>();
        longmap.put(value,value);
        return longmap;
    }
}
