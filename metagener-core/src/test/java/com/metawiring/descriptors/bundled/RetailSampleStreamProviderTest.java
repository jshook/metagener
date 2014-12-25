package com.metawiring.descriptors.bundled;

import com.metawiring.clientapi.EntitySampleStream;
import com.metawiring.clientapi.SampleStreamProvider;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.testng.Assert.*;

public class RetailSampleStreamProviderTest {

    @BeforeMethod
    public void setUp() throws Exception {

    }

    @Test
    public void testGet() throws Exception {
        SampleStreamProvider ss = RetailSampleStreamProvider.get();
        assertNotNull(ss);
    }

    @Test
    public void testGetSampleStreamMap() throws Exception {
        SampleStreamProvider ss = RetailSampleStreamProvider.get();
        Map<String, EntitySampleStream> ssmap = ss.getSampleStreamMap();
        assertThat(ssmap.values().size(),greaterThan(0));

    }

}