package com.metawiring.generation.fieldfunctions;

import com.metawiring.generation.FieldFunction;

import java.util.ArrayList;
import java.util.List;

public class BoxedList implements FieldFunction<List<Long>> {
    @Override
    public List<Long> apply(long value) {
        ArrayList<Long> list = new ArrayList<Long>();
        list.add(value);
        return list;
    }
}
