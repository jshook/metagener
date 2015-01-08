package com.metawiring.generation.fieldgenboxes;

import com.metawiring.types.FieldFunctionSignature;
import com.metawiring.types.functiontypes.TypedFieldFunction;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

@FieldFunctionSignature(input=Long.class,output=InetAddress.class)
public class BoxedInetAddress implements TypedFieldFunction<InetAddress> {

    ByteBuffer bb = ByteBuffer.allocate(Integer.BYTES);

    @Override
    public InetAddress apply(long value) {
        bb.clear();
        bb.putInt((int) value);
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
