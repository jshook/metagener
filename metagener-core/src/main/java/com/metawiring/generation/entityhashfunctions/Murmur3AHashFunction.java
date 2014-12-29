package com.metawiring.generation.entityhashfunctions;

import com.metawiring.types.EntityHashFunction;
import de.greenrobot.common.hash.Murmur3A;

public class Murmur3AHashFunction implements EntityHashFunction {

    private Murmur3A murmur3A = new Murmur3A();

    @Override
    public long applyAsLong(long value) {
        murmur3A.updateLong(value);
        return murmur3A.getValue();
    }
}
