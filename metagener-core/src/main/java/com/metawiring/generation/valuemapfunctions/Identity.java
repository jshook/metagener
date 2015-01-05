package com.metawiring.generation.valuemapfunctions;


import com.metawiring.annotations.FieldFunctionSignature;
import com.metawiring.generation.FieldFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FieldFunctionSignature(input=Long.class,output=Long.class)
public class Identity implements FieldFunction<Long,Long> {
    private final static Logger logger = LoggerFactory.getLogger(Identity.class);

    @Override
    public Long apply(Long aLong) {
        logger.info("Identity:" + aLong);
        return aLong;
    }
}
