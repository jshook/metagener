package com.metawiring.generation.fieldgenfuncs;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.*;

public class NumbNamerTest {

    @Test
    public void testNumbNamerCache() {
        NumbNamer numbNamer = new NumbNamer();
        String result;
        result= numbNamer.apply(8675309l);
        assertThat(result).isEqualTo("esisefitrezeni");
        result= numbNamer.apply(123456789101112l);
        assertThat(result).isEqualTo("ototrefofisiseeniozeoooto");
        result= numbNamer.apply(24601l);
        assertThat(result).isEqualTo("tofosizeo");
    }

}