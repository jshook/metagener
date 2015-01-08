package com.metawiring;

import org.testng.annotations.Test;

import java.util.function.Function;

public class FuncTypeTest {

    @Test
    public void testChaining() {
        int i1=234;
        IntToLongImpl f1 = new IntToLongImpl();
        LongToStringImpl f2 = new LongToStringImpl();

        f2.apply(f1.apply(i1));

        Function<Integer, String> f3 = f2.compose(f1);




    }


    private class IntToLongImpl implements IntToLong {
        @Override
        public Long apply(Integer integer) {
            return Long.valueOf(integer);
        }
    }

    private class LongToStringImpl implements LongToString {
        @Override
        public String apply(Long aLong) {
            return String.valueOf(aLong);
        }
    }

    @FunctionalInterface
    private static interface IntToLong extends Function<Integer, Long> {
    }

    @FunctionalInterface
    private static interface LongToString extends Function<Long,String> {
    }
}
