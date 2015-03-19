package com.metawiring.types.functiontypes;

import com.metawiring.annotations.Input;
import com.metawiring.annotations.Output;
import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface GenericFieldFunction<T, R> extends Function<T, R> {

    default Class<?>[] getOutputTypeSignature() {
        Output output = getClass().getAnnotation(Output.class);
        Objects.requireNonNull(output, "Output annotation was not found on " + getClass().getCanonicalName());
        return output.value();
    }

    default Class<?>[] getInputTypeSignature() {
        Input input = getClass().getAnnotation(Input.class);
        Objects.requireNonNull(input, "Input annotation was not found on " + getClass().getCanonicalName());
        return input.value();
    }

    default void verifyInputChainSignature(Class<?> previousFunction) {
        Class<?>[] validInputs = getInputTypeSignature();
        switch (previousFunction.getSimpleName()) {
            case "LongUnaryFieldFunction":
                break;
            case "TypedFieldFunction":
            case "GenericFieldFunction":
                Output outputAnnotation = previousFunction.getAnnotation(Output.class);
                Class<?>[] inboundTypes = outputAnnotation.value();
                Objects.requireNonNull(outputAnnotation,"Output annotation was not found on " + previousFunction.getCanonicalName());
                for (int paramidx = 0; paramidx < validInputs.length; paramidx++) {
                    if (!validInputs[paramidx].getCanonicalName().equals(inboundTypes[paramidx].getCanonicalName())) {
                        throw new RuntimeException("Output signature of " + previousFunction.getCanonicalName()
                        + " does not match input signature of " + getClass().getCanonicalName()
                                + ", param idx: " + paramidx
                                + ", output:" + inboundTypes[paramidx]
                                + ", input:" + validInputs[paramidx]
                        );
                    }
                }
                break;
            default:
                throw new RuntimeException(
                        "Unable to verify input chain signature when previous function is of unknown structure: "
                                + previousFunction.getCanonicalName()
                );
        }

    }

    default <I> GenericFieldFunction<Long, R> compose(TypedFieldFunction<T> before) {
        Objects.requireNonNull(before);
        return (Long input) -> apply(before.apply(input));
    }

    default <I> GenericFieldFunction<I, R> compose(GenericFieldFunction<I, T> before) {
        Objects.requireNonNull(before);
        return (I i) -> apply(before.apply(i));
    }

    default <V> GenericFieldFunction<T, V> andThen(GenericFieldFunction<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

}
