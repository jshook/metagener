package com.metawiring.types.functiontypes;

import java.util.function.LongUnaryOperator;

/**
 * Composable functions that work on primitive longs.
 * This is an explicit type to work around the problem with boxing and
 * inconsistent treatment of compose(...) and andThen(...) in J8
 */
public interface LongFieldFunction extends LongUnaryOperator {

    default LongFieldFunction compose(LongFieldFunction inner) {
        return (long input) -> applyAsLong(inner.applyAsLong(input));
    }

    default LongFieldFunction andThen(LongFieldFunction outer) {
        return (long input) -> outer.applyAsLong(applyAsLong(input));
    }

    default <R> TypedFieldFunction<R> andThen(TypedFieldFunction<R> outer) {
        return (long input) -> outer.apply(applyAsLong(input));
    }

}
