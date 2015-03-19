package com.metawiring.generation.fieldgenboxes;

import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.TypedFieldFunction;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

@Output({Set.class})
public class BoxedSet implements TypedFieldFunction<Set<Long>> {

    @Override
    public Set<Long> apply(long value) {
        Set<Long> set = new HashSet<Long>();
        set.add(value);
        return set;
    }
}
