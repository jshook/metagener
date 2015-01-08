package com.metawiring.generation.core;

import com.metawiring.configdefs.FormatConstants;
import com.metawiring.types.functiontypes.FieldFunction;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;


public class FieldFunctionResolver {
    private final static Logger logger = LoggerFactory.getLogger(FieldFunctionResolver.class);

    public static FieldFunction resolveFieldFunction(String functionSpec) {
        FunctionDef fd = parseFunctionDef(functionSpec);

        Class<? extends FieldFunction> fieldFunctionClass = null;

        try {
            FieldFunctionName fieldFunctionName = FieldFunctionName.valueOf(fd.name);
            fieldFunctionClass = fieldFunctionName.getImplClass();
        } catch (IllegalArgumentException e) {
            logger.info("Named function [" + fd.name + "] not found, falling back to class name resolver.");
        }

        if (fieldFunctionClass == null) {
            try {
                fieldFunctionClass = FunctionClassFinder.find(fd.name);
            } catch (Exception e) {
                logger.error("Named function [" + fd.name + "] was not found by class either." + e.getMessage());
                throw e;
            }
        }

        try {
            FieldFunction fieldFunction = ConstructorUtils.invokeConstructor(
                    fieldFunctionClass,
                    (Object[]) fd.arguments
            );
            return fieldFunction;
        } catch (Exception e) {
            logger.error("Unable to instantiate class [" + fd + "]", e);
            throw new RuntimeException("Unable to instantiate class [" + fd + "]", e);
        }

    }

    private static FunctionDef parseFunctionDef(String functionSpec) {
        String[] funcAndArgs = functionSpec.split(FormatConstants.FUNCNAME_TERMINAL, 2);
        String[] args = new String[0];
        if (funcAndArgs.length == 2) {
            args = funcAndArgs[1].split(FormatConstants.ARG_DELIM);
        }

        FunctionDef fd = new FunctionDef();
        fd.name = funcAndArgs[0];
        fd.arguments = args;
        return fd;
    }

    private static class FunctionDef {
        public String name;
        public String[] arguments;

        public String toString() {
            return name + Arrays.toString(arguments);
        }
    }
}
