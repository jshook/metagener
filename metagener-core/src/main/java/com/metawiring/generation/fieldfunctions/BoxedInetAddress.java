package com.metawiring.generation.fieldfunctions;

import com.metawiring.generation.FieldFunction;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class BoxedInetAddress implements FieldFunction<InetAddress> {
    @Override
    public InetAddress apply(long value) {

        ByteBuffer bb = ByteBuffer.allocate(Long.BYTES);
        bb.putLong(value);
        bb.flip();

        InetAddress addr = null;
        try {
            addr = Inet4Address.getByAddress(bb.array());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return addr;
    }
}
