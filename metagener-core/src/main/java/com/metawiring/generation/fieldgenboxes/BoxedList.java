package com.metawiring.generation.fieldgenboxes;

import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.TypedFieldFunction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Output({List.class})
public class BoxedList implements TypedFieldFunction<List<Long>> {

    @Override
    public List<Long> apply(long value) {
        ArrayList<Long> list = new ArrayList<Long>();
        list.add(value);
        return list;
    }
}
