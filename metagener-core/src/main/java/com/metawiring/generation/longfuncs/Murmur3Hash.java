package com.metawiring.generation.longfuncs;

import com.metawiring.types.functiontypes.LongUnaryFieldFunction;
import de.greenrobot.common.hash.Murmur3F;
import java.nio.ByteBuffer;

/**
 * This uses the Murmur3F (64-bit optimized) version of Murmur3, not as a checksum, but as a simple hash.
 * It doesn't bother pushing the high-64 bits of input, since it only uses the lower 64 bits of output.
 * It does, however, return the absolute value. This is to make it play nice with users and other libraries.
 */
public class Murmur3Hash implements LongUnaryFieldFunction {

    private ByteBuffer bytes = ByteBuffer.allocate(Long.BYTES);
    private Murmur3F murmur3F= new Murmur3F();

    @Override
    public long applyAsLong(long value) {
        murmur3F.reset();
        bytes.clear();
        bytes.putLong(value);
        bytes.flip();
        murmur3F.update(bytes.array());
        long result= Math.abs(murmur3F.getValue());
        return result;
    }
}
