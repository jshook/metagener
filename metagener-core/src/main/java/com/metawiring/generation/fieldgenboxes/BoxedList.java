package com.metawiring.generation.fieldgenboxes;

import com.metawiring.types.functiontypes.TypedFieldFunction;

import java.util.ArrayList;
import java.util.List;

public class BoxedList implements TypedFieldFunction<List<Long>> {

    @Override
    public List<Long> apply(long value) {
        ArrayList<Long> list = new ArrayList<Long>();
        list.add(value);
        return list;
    }
}
