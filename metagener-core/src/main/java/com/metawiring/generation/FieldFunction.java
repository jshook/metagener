package com.metawiring.generation;

import java.util.function.LongFunction;

@FunctionalInterface
public interface FieldFunction<T> extends LongFunction<T> {
}
