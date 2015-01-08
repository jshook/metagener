package com.metawiring;

import com.metawiring.generation.entityid.Murmur3Hash;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class Murmur3FuncTest {

    @Test
    public void testMurmur3FHashFunctionIsPure() {
        Murmur3Hash m3f = new Murmur3Hash();

        long h1a = m3f.applyAsLong(1);
        long h2a = m3f.applyAsLong(2);
        long h1b = m3f.applyAsLong(1);
        long h2b = m3f.applyAsLong(2);

        assertThat(h1a,equalTo(h1b));
        assertThat(h2a,equalTo(h2b));

    }
}
