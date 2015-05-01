package com.metawiring.types.functiontypes;

import java.util.function.LongUnaryOperator;

/**
 * Composable functions that work on primitive longs.
 * This is an explicit type to work around the problem with boxing and
 * inconsistent treatment of compose(...) and andThen(...) in J8
 */
public interface LongUnaryFieldFunction extends LongUnaryOperator {

    default LongUnaryFieldFunction compose(LongUnaryFieldFunction inner) {
        return (long input) -> applyAsLong(inner.applyAsLong(input));
    }

    default LongUnaryFieldFunction andThen(LongUnaryFieldFunction outer) {
        return (long input) -> outer.applyAsLong(applyAsLong(input));
    }

    default <R> TypedFieldFunction<R> andThen(TypedFieldFunction<R> outer) {
        return (long input) -> outer.apply(applyAsLong(input));
    }

    default <R extends Long> TypedFieldFunction<? extends Long> composeLongUnary(TypedFieldFunction<? extends Long> composedFunction) {
        return (long input) -> applyAsLong(composedFunction.apply(input));
    }
}
