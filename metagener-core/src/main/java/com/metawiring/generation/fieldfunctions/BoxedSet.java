package com.metawiring.generation.fieldfunctions;

import com.metawiring.generation.FieldFunction;

import java.util.HashSet;
import java.util.Set;

public class BoxedSet implements FieldFunction<Set<Long>> {
    @Override
    public Set<Long> apply(long value) {
        Set<Long> set = new HashSet<Long>();
        set.add(value);
        return set;
    }
}
