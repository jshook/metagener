package com.metawiring.types.functiontypes;

import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;

/**
 * Composable functions that work on primitive longs.
 * This is an explicit type to work around the problem with boxing and
 * inconsistent treatment of compose(...) and andThen(...) in J8
 */
public interface LongBinaryFieldFunction extends LongBinaryOperator {

    default LongBinaryFieldFunction compose(LongUnaryFieldFunction leftFunc, LongUnaryFieldFunction rightFunc) {
        return (long left, long right) -> applyAsLong(leftFunc.applyAsLong(left),rightFunc.applyAsLong(right));
    }

    default LongUnaryFieldFunction andThen(LongUnaryFieldFunction outer) {
        return (long input) -> outer.applyAsLong(applyAsLong(input));
    }

    default <R> TypedFieldFunction<R> andThen(TypedFieldFunction<R> outer) {
        return (long input) -> outer.apply(applyAsLong(input));
    }

}
