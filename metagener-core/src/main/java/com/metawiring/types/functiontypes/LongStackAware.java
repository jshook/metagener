package com.metawiring.types.functiontypes;

import com.metawiring.types.EntityDef;

/**
 * <p>
 * If a function implements this interface, then it will be initialized with an array of longs
 * which is shared among all other LongStackAware functions in the same composed function.
 * </p>
 *
 * <p>
 * This stack has a stack pointer which is stored in stack[0]. The stack pointer is modified post-store,
 * and the next available slot is pre-increment form the current stack pointer.
 * </p>
 */
public interface LongStackAware {
    /**
     * Provide access to the long stack.
     * @param longStack the long stack
     */
    public void applyLongStack(long[] longStack);
}

