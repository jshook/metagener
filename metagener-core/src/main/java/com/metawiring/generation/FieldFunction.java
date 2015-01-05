package com.metawiring.generation;

import com.metawiring.annotations.FieldFunctionSignature;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface FieldFunction<T,R> extends Function<T,R> {

    default Class<?> getOutputType() {
        FieldFunctionSignature fsThis = getClass().getAnnotation(FieldFunctionSignature.class);
        Objects.requireNonNull(fsThis, "FunctionSignature was not found on " + getClass().getCanonicalName());
        return fsThis.output();
    }

    default void verifyInputType(Class<?> inputType) {
        FieldFunctionSignature fsThis = getClass().getAnnotation(FieldFunctionSignature.class);
        Objects.requireNonNull(fsThis);
        if (!inputType.isAssignableFrom(fsThis.input())) {
            throw new RuntimeException("type " + inputType + " is not assignable from " + fsThis.input()
            + " for function: " + this.getClass().getCanonicalName());
        }
    }

    default void verifyOutputType(Class<?> outputType) {
        FieldFunctionSignature fsThis = getClass().getAnnotation(FieldFunctionSignature.class);
        Objects.requireNonNull(fsThis);
        if (!outputType.isAssignableFrom(fsThis.output())) {
            throw new RuntimeException(outputType + " is not assignable from " + fsThis.output()
                    + " for function: " + this.getClass().getCanonicalName());
        }

    }

    default <V> FieldFunction<T, V> andThen(FieldFunction<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

}
