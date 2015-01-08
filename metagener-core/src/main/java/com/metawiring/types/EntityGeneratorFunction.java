package com.metawiring.types;

import java.util.function.LongFunction;

@FunctionalInterface
public interface EntityGeneratorFunction<EntitySample> extends LongFunction<EntitySample> {
}
