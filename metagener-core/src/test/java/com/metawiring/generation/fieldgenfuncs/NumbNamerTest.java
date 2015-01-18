package com.metawiring.generation.fieldgenfuncs;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.*;

public class NumbNamerTest {

    @Test
    public void testNumbNamerCache() {
        NumbNamer numbNamer = new NumbNamer();
        String result;
        result= numbNamer.apply(8675309l);
        assertThat(result,is("esisefitrezeni"));
        result= numbNamer.apply(123456789101112l);
        assertThat(result,is("ototrefofisiseeniozeoooto"));
        result= numbNamer.apply(24601l);
        assertThat(result,is("tofosizeo"));
    }

}