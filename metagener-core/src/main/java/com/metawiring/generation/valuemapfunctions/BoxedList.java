package com.metawiring.generation.valuemapfunctions;

import com.metawiring.generation.FieldFunction;

import java.util.ArrayList;
import java.util.List;

public class BoxedList implements FieldFunction<Long,List<Long>> {

    @Override
    public List<Long> apply(Long aLong) {
        ArrayList<Long> list = new ArrayList<Long>();
        list.add(aLong);
        return list;
    }
}
