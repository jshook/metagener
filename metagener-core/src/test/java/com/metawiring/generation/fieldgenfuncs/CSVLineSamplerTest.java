package com.metawiring.generation.fieldgenfuncs;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.*;

public class CSVLineSamplerTest {

    @Test
    public void testCSVLineSampler() {
        CSVLineSampler cls = new CSVLineSampler("testlines.csv","quoted","uniform");
        assertThat(cls.apply(1),is("zero"));
        assertThat(cls.apply(Long.MAX_VALUE/2),is("eight"));
        assertThat(cls.apply(Long.MAX_VALUE),is("sixteen"));
        // For 'uniform', ICD is flat
    }

}