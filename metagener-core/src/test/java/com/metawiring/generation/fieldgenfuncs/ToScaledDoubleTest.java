package com.metawiring.generation.fieldgenfuncs;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ToScaledDoubleTest {

    @Test
    public void testFullLongRange() {
        ToScaledDouble fullRange = new ToScaledDouble((double)Long.MIN_VALUE,(double)Long.MAX_VALUE);
        assertThat(fullRange.apply(Long.MIN_VALUE),is((double)Long.MIN_VALUE));
        assertThat(fullRange.apply(0l),is(0.0d));
        assertThat(fullRange.apply(Long.MAX_VALUE),is((double)Long.MAX_VALUE));
    }

    @Test
    public void testApply5to500() throws Exception {
        ToScaledDouble toDecimal5ToBig500 = new ToScaledDouble(5,500);
        assertThat(toDecimal5ToBig500.apply(Long.MAX_VALUE),is(500.0d));
        assertThat(toDecimal5ToBig500.apply(Long.MIN_VALUE),is(5.0d));
        assertThat(toDecimal5ToBig500.apply(0l),is(5.0d+((500d-5d)/2.0d)));
    }

    @Test
    public void testApplyFractional0005to125() throws Exception {
        ToScaledDouble toScaledDouble0005to125 = new ToScaledDouble("0.0005",".125");
        assertThat(toScaledDouble0005to125.apply(Long.MIN_VALUE),is(0.0005d));
        assertThat(toScaledDouble0005to125.apply(0l),is(.0627));
        assertThat(toScaledDouble0005to125.apply(Long.MAX_VALUE),is(0.125d));

    }
}