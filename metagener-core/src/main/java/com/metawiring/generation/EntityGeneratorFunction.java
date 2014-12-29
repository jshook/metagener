package com.metawiring.generation;

import java.util.function.LongFunction;

@FunctionalInterface
public interface EntityGeneratorFunction<EntitySample> extends LongFunction<EntitySample> {
}
