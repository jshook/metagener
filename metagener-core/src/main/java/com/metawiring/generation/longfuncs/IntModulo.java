package com.metawiring.generation.longfuncs;

import com.metawiring.types.FieldFunctionSignature;
import com.metawiring.types.functiontypes.GenericFieldFunction;

@FieldFunctionSignature(input=Long.class,output = Long.class)
public class IntModulo implements GenericFieldFunction<Long,Long> {

    @Override
    public Long apply(Long aLong) {
        return (aLong % Integer.MAX_VALUE);
   }
}
