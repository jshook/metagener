package com.metawiring.types.functiontypes;

import java.util.function.LongFunction;

/**
 * Composable functions that work on primitive longs.
 * R is the result type of the function. The input type is always long for this type.
 */
public interface TypedFieldFunction<R> extends FieldFunction,LongFunction<R> {

    default <T> TypedFieldFunction compose(LongFieldFunction before) {
        return (long input) -> apply(before.applyAsLong(input));
    }

    default <S> GenericFieldFunction<Long,S> andThen(GenericFieldFunction<R,S> after) {
        return (Long input) -> after.apply(apply(input));
    }

}
