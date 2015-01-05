package com.metawiring.generation.valuemapfunctions;

import com.metawiring.annotations.FieldFunctionSignature;
import com.metawiring.generation.FieldFunction;

@FieldFunctionSignature(input=Long.class,output=Long.class)
public class Modulo implements FieldFunction<Long,Long> {
    private long moduloBy;

    public Modulo(long moduloBy) {
        this.moduloBy = moduloBy;
    }

    @Override
    public Long apply(Long aLong) {
        return aLong % moduloBy;
    }
}
