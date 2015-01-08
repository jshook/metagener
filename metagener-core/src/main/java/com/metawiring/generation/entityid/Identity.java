package com.metawiring.generation.entityid;


import com.metawiring.types.FieldFunctionSignature;
import com.metawiring.types.functiontypes.GenericFieldFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FieldFunctionSignature(input=Long.class,output=Long.class)
public class Identity implements GenericFieldFunction<Long,Long> {
    private final static Logger logger = LoggerFactory.getLogger(Identity.class);

    @Override
    public Long apply(Long aLong) {
        logger.info("Identity:" + aLong);
        return aLong;
    }
}
