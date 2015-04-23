package com.metawiring.generation.fieldgenfuncs;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CSVLineSamplerTest {

    @Test
    public void testCSVLineSampler() {
        CSVLineSampler cls = new CSVLineSampler("testlines.csv","quoted","uniform");
        assertThat(cls.apply(1)).isEqualTo("zero");
        assertThat(cls.apply(Long.MAX_VALUE/2)).isEqualTo("eight");
        assertThat(cls.apply(Long.MAX_VALUE)).isEqualTo("sixteen");
        // For 'uniform', ICD is flat
    }

}