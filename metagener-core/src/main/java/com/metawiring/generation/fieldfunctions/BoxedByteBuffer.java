package com.metawiring.generation.fieldfunctions;

import com.metawiring.generation.FieldFunction;

import java.nio.ByteBuffer;

public class BoxedByteBuffer implements FieldFunction<ByteBuffer> {
    @Override
    public ByteBuffer apply(long value) {
        ByteBuffer bytes = ByteBuffer.allocate(4);
        bytes.putLong(value);
        bytes.flip();
        return bytes;
    }
}
