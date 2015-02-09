package com.metawiring.generation.fieldgenfuncs;

import com.metawiring.types.functiontypes.GenericFieldFunction;

import java.nio.CharBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.function.IntUnaryOperator;

public class NumbNamer implements GenericFieldFunction<Long, String> {

    private static String[] digitNames = new String[]{
            "ze", "o", "to", "tre", "fo", "fi", "si", "se", "e", "ni"
    };

    private static String[] cachedValues;

    public NumbNamer() {
        preComputeCache();
    }

    private void preComputeCache() {
        synchronized (NumbNamer.class) {
            if (cachedValues!=null) {
                return;

            }
            cachedValues = new String[1000];
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < 1000; i++) {
                sb.setLength(0);
                String.valueOf(i).chars().forEach(new IntConsumer() {
                    @Override
                    public void accept(int value) {
                        sb.append(digitNames[value - 48]);
                    }
                });
                cachedValues[i]=sb.toString();
            }
        }
    }


    private String[] stack = new String[10];
    private StringBuilder numbName= new StringBuilder();

    @Override
    public String apply(Long aLong) {
        numbName.setLength(0);
        long accum= aLong;
        int pos = -1;
        while (accum>0) {
            long big= (accum / 1000);
            long small = (accum - big*1000);
            accum = big;

            pos++;
            stack[pos]=cachedValues[((int) small)];
        }
        for(int p=pos;p>=0;p--) {
            numbName.append(stack[p]);
        }
        return numbName.toString();
    }
}