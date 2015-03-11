package com.metawiring.generation.fieldgenboxes;

import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.TypedFieldFunction;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Output({Map.class})
public class BoxedMap implements TypedFieldFunction<Map<Long,Long>> {

    @Override
    public Map<Long, Long> apply(long value) {
        HashMap<Long,Long> longmap = new HashMap<Long,Long>();
        longmap.put(value,value);
        return longmap;
    }
}
