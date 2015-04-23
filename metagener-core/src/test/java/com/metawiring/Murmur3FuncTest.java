package com.metawiring;

import com.metawiring.generation.longfuncs.Murmur3Hash;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Murmur3FuncTest {

    @Test
    public void testMurmur3FHashFunctionIsPure() {
        Murmur3Hash m3f = new Murmur3Hash();

        long h1a = m3f.applyAsLong(1);
        long h2a = m3f.applyAsLong(2);
        long h1b = m3f.applyAsLong(1);
        long h2b = m3f.applyAsLong(2);

        assertThat(h1a).isEqualTo(h1b);
        assertThat(h2a).isEqualTo(h2b);

    }
}
