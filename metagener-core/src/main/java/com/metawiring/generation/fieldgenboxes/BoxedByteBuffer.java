package com.metawiring.generation.fieldgenboxes;

import com.metawiring.types.functiontypes.TypedFieldFunction;

import java.nio.ByteBuffer;

public class BoxedByteBuffer implements TypedFieldFunction<ByteBuffer> {

    ByteBuffer bytes = ByteBuffer.allocate(Long.BYTES);

    @Override
    public ByteBuffer apply(long value) {
        bytes.clear();
        bytes.putLong(value);
        bytes.flip();
        return bytes;
    }
}
