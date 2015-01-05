package com.metawiring.generation.valuemapfunctions;

import com.metawiring.generation.FieldFunction;

import java.nio.ByteBuffer;

public class BoxedByteBuffer implements FieldFunction<Long,ByteBuffer> {

    @Override
    public ByteBuffer apply(Long aLong) {
        ByteBuffer bytes = ByteBuffer.allocate(4);
        bytes.putLong(aLong);
        bytes.flip();
        return bytes;
    }
}
