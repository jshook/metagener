package com.metawiring.generation.boxedfields;

import com.metawiring.generation.fieldgenboxes.*;
import com.metawiring.types.functiontypes.TypedFieldFunction;
import org.joda.time.DateTime;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BoxedFieldFunctionsTest {

    @Test
    public void testBoxedBigInt() {
        TypedFieldFunction<BigInteger> ff = new BoxedBigInteger();
        BigInteger actual= ff.apply(12345l);
        BigInteger expected = BigInteger.valueOf(12345l);
        assertThat(actual, is(expected));
    }

    @Test
    public void testBoxedBoolean() {
        TypedFieldFunction<Boolean> ff = new BoxedBoolean();

        Boolean actualTrue = ff.apply(12346l);
        assertThat(actualTrue,is(true));

        Boolean actualFalse = ff.apply(12345l);
        assertThat(actualFalse,is(false));
    }

    @Test
    public void testBoxedByteBuffer() {
        TypedFieldFunction<ByteBuffer> ff = new BoxedByteBuffer();
        ByteBuffer actual = ff.apply(12345l);
        ByteBuffer expected = ByteBuffer.allocate(Long.BYTES);
        expected.putLong(12345l);
        expected.flip();
        assertThat(actual,is(expected));
    }

    @Test
    public void testBoxedDouble() {
        TypedFieldFunction<Double> ff = new BoxedDouble();
        Double actual = ff.apply(12345l);
        Double expected = Double.valueOf("12345");
        assertThat(actual,is(expected));
    }

    @Test
    public void testBoxedFloat() {
        TypedFieldFunction<Float> ff = new BoxedFloat();
        Float actual = ff.apply(12345l);
        Float expected = Float.valueOf("12345");
        assertThat(actual,is(expected));

    }

    @Test
    public void testBoxedInetAddress() {
        TypedFieldFunction<InetAddress> ff = new BoxedInetAddress();
        ByteBuffer bb = ByteBuffer.allocate(Integer.BYTES);
        bb.putInt((int)12345l).flip();

        InetAddress actual = ff.apply(12345l);
        InetAddress expected = null;
        try {
            expected = InetAddress.getByAddress(bb.array());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        assertThat(actual,is(expected));
    }

    @Test
    public void testBoxedInt() {
        TypedFieldFunction<Integer> ff = new BoxedInteger();
        Integer actual = ff.apply(12345l);
        Integer expected = Integer.valueOf("12345");
        assertThat(actual,is(expected));
    }

    @Test
    public void testBoxedList() {
        TypedFieldFunction<List<Long>> ff = new BoxedList();
        List<Long> actual = ff.apply(12345l);
        List<Long> expected = Arrays.asList(12345l);
        assertThat(actual,is(expected));
    }

    @Test
    public void testBoxedLong() {
        TypedFieldFunction<Long> ff = new BoxedLong();
        Long actual = ff.apply(12345l);
        Long expected = 12345l;
        assertThat(actual,is(expected));
    }

    @Test
    public void testBoxedMap() {
        TypedFieldFunction<Map<Long,Long>> ff = new BoxedMap();
        Map<Long,Long> actual = ff.apply(12345l);
        Map<Long,Long> expected = new HashMap<>();
        expected.put(12345l, 12345l);
        assertThat(actual,is(expected));
    }

    @Test
    public void testBoxedSet() {
        TypedFieldFunction<Set<Long>> ff = new BoxedSet();
        Set<Long> actual = ff.apply(12345l);
        Set<Long> expected = new HashSet<>();
        expected.add(12345l);
        assertThat(actual, is(expected));
    }

    @Test
    public void testBoxedString() {
        TypedFieldFunction<String> ff = new BoxedString();
        String actual = ff.apply(12345l);
        String expected = "12345";
        assertThat(actual,is(expected));
    }

    @Test
    public void testBoxedTimeStamp() {
        TypedFieldFunction<DateTime> ff = new BoxedDateTime();
        DateTime actual = ff.apply(12345l);
        DateTime expected = new DateTime(12345l);
        assertThat(actual,is(expected));
    }

    @Test
    public void testBoxedUUID() {
        TypedFieldFunction<UUID> ff = new BoxedUUID();
        UUID actual = ff.apply(12345l);
        UUID expected = new UUID(12345l,12345l);
        // TODO: Get UUIDs working in a useful way
        assertThat(actual, is(expected));
    }

}