package com.metawiring.generation.core;

import com.metawiring.types.FuncCallDef;
import com.metawiring.types.functiontypes.LongUnaryFieldFunction;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FieldFunctionResolver {
    private final static Logger logger = LoggerFactory.getLogger(FieldFunctionResolver.class);

    public static Object resolveAndCreateFunctionObject(FuncCallDef functionSpec) {

        Class<?> functionClass = null;

        try {
            FieldFunctionName fieldFunctionName = FieldFunctionName.valueOf(functionSpec.getFuncName());
            functionClass = fieldFunctionName.getImplClass();
        } catch (IllegalArgumentException e) {
            logger.info("Named function [" + functionSpec.getFuncName() + "] not found, falling back to class name resolver.");
        }

        if (functionClass == null) {
            try {
                functionClass = FunctionClassFinder.find(functionSpec.getFuncName());
            } catch (Exception e) {
                logger.error("Named function [" + functionSpec.getFuncName() + "] was not found by class either." + e.getMessage());
                throw e;
            }
        }

        try {
            @SuppressWarnings("unchecked")
            Object[] args = (Object[]) functionSpec.getFuncArgs().toArray();
            Class<LongUnaryFieldFunction> funcClass = (Class<LongUnaryFieldFunction>) functionClass;
            Object fieldFunction = ConstructorUtils.invokeConstructor(funcClass,args);
            return fieldFunction;
        } catch (Exception e) {
            logger.error("Unable to instantiate class [" + functionClass + "] for function call:" + functionSpec, e);
            throw new RuntimeException("Unable to instantiate class [" + functionClass + "]", e);
        }

    }
}
