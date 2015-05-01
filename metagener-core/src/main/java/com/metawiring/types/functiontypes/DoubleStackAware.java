package com.metawiring.types.functiontypes;

import java.util.Stack;

/**
 * <p>
 * If a function implements this interface, then it will be initialized with an Stack<Double>
 * which is shared among all other DoubleStackAware functions in the same composed function.
 * </p>
 */
public interface DoubleStackAware {
    /**
     * Provide access to the long stack.
     * @param doubleStack the long stack
     */
    public void applyDoubleStack(Stack<Double> doubleStack);
}

