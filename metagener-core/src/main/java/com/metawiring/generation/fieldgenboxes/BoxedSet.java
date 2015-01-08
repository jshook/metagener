package com.metawiring.generation.fieldgenboxes;

import com.metawiring.types.functiontypes.TypedFieldFunction;

import java.util.HashSet;
import java.util.Set;

public class BoxedSet implements TypedFieldFunction<Set<Long>> {

    @Override
    public Set<Long> apply(long value) {
        Set<Long> set = new HashSet<Long>();
        set.add(value);
        return set;
    }
}
