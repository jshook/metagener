package com.metawiring.generation.valuemapfunctions;

import com.metawiring.generation.FieldFunction;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class BoxedInetAddress implements FieldFunction<Long,InetAddress> {

    @Override
    public InetAddress apply(Long aLong) {
        ByteBuffer bb = ByteBuffer.allocate(Long.BYTES);
        bb.putLong(aLong);
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
