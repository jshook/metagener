package com.metawiring.types.functiontypes;

import com.metawiring.types.FieldFunctionSignature;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface GenericFieldFunction<T,R> extends Function<T,R> {

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

    default <I> GenericFieldFunction<Long,R> compose(TypedFieldFunction<T> before) {
        Objects.requireNonNull(before);
        return (Long input) -> apply(before.apply(input));
    }

    default <I> GenericFieldFunction<I,R> compose(GenericFieldFunction<I, T> before) {
        Objects.requireNonNull(before);
        return (I i) -> apply(before.apply(i));
    }

    default <V> GenericFieldFunction<T, V> andThen(GenericFieldFunction<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

}
