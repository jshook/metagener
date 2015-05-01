package com.metawiring.types.functiontypes;

import java.util.Objects;
import java.util.function.LongFunction;
import java.util.function.LongUnaryOperator;

/**
 * Composable functions that work on primitive longs.
 * R is the result type of the function. The input type is always long for this type.
 *
 * <p>
 *     Typed field functions represent the basic functional type that is used elsewhere in this
 *     system. A primitive long goes in, and a value of type R comes out. An extension of this
 *     is that you can put as many (long -> long) functions inside the composed function as you want.
 *     Also, you can put as many (Function&lt;L,R&gt; -> Function&lt;L,R&gt;) on the outside as you want.
 * </p>
 *
 * <p>
 *     The composed function is broken apart in this way for a couple of reasons:
 *     <OL>
 *         <li>Having a primitive specialized function for long mappings will avoid a serious amount of boxing.</li>
 *         <li>It still allows for full generic use of the functional interface. Runtime type checks will still work.</li>
 *     </OL>
 * </p>
 */
public interface TypedFieldFunction<R> extends LongFunction<R> {

    default <T> TypedFieldFunction compose(LongUnaryFieldFunction before) {
        Objects.requireNonNull(before);
        return (long input) -> apply(before.applyAsLong(input));
    }

    default <S> TypedFieldFunction<S> andThen(GenericFieldFunction<R,S> after) {
        Objects.requireNonNull(after);
        return (long input) -> after.apply(apply(input));
    }

    default TypedFieldFunction<R> composeLongUnary(TypedFieldFunction<? extends Long> before) {
        Objects.requireNonNull(before);
        return (long input) -> apply(before.apply(input));
    }


    default TypedFieldFunction<R> compose(LongUnaryOperator before) {
        Objects.requireNonNull(before);
        return (long input) -> apply(before.applyAsLong(input));
    }


}
