package com.metawiring.generation.valuemapfunctions;

import com.metawiring.generation.FieldFunction;

import java.util.HashSet;
import java.util.Set;

public class BoxedSet implements FieldFunction<Long,Set<Long>> {

    @Override
    public Set<Long> apply(Long aLong) {
        Set<Long> set = new HashSet<Long>();
        set.add(aLong);
        return set;
    }
}
