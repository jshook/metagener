package com.metawiring.generation.longfuncs;


import com.metawiring.types.functiontypes.LongUnaryFieldFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggedIdentity implements LongUnaryFieldFunction {
    private final static Logger logger = LoggerFactory.getLogger(LoggedIdentity.class);

    @Override
    public long applyAsLong(long aLong) {
        logger.info("Identity:" + aLong);
        return aLong;
    }
}
