package com.metawiring.annotations;

import java.lang.annotation.*;

/**
 * Retain a function signature into runtime, for intentional type checking with
 * composed functions.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FieldFunctionSignature {
    Class<?> input();
    Class<?> output();

}
