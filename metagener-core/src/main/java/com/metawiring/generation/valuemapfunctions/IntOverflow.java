package com.metawiring.generation.valuemapfunctions;

import com.metawiring.annotations.FieldFunctionSignature;
import com.metawiring.generation.FieldFunction;

@FieldFunctionSignature(input=Long.class,output = Long.class)
public class IntOverflow implements FieldFunction<Long,Long> {

    @Override
    public Long apply(Long aLong) {
        return (aLong % Integer.MAX_VALUE);
   }
}
