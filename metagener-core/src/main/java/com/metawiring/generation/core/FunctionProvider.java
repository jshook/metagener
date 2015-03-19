package com.metawiring.generation.core;

public interface FunctionProvider<T> {
    public T init(String... initialFunctionParameters);
}
