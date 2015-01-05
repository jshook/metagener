package com.metawiring.generation.valuemapfunctions;

import com.metawiring.annotations.FieldFunctionSignature;
import com.metawiring.generation.FieldFunction;

/**
 * Simple modulo by a number.
 */
@FieldFunctionSignature(input=Long.class,output=Long.class)
public class Modulo implements FieldFunction<Long,Long> {

    private final long modulo;

    public Modulo(long modulo) {
        this.modulo = modulo;
    }

    @Override
    public Long apply(Long aLong) {
        return aLong % modulo;
    }
}
