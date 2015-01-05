package com.metawiring.generation.valuemapfunctions;

import com.metawiring.generation.FieldFunction;

public class BoxedBoolean implements FieldFunction<Long,Boolean> {
    @Override
    public Boolean apply(Long aLong) {
        return (aLong % 2)==0;
    }
}
