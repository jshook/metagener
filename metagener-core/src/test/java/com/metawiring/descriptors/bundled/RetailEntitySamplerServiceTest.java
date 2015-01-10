package com.metawiring.descriptors.bundled;

import com.metawiring.types.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.testng.Assert.*;

public class RetailEntitySamplerServiceTest {
    private final static Logger logger = LoggerFactory.getLogger(RetailEntitySamplerServiceTest.class);

    @BeforeMethod
    public void setUp() throws Exception {

    }

    @Test
    public void testGet() throws Exception {
        PrebundledStreams pbs = new PrebundledStreamsImpl();
        EntitySamplerService ess  = pbs.getRetailStreams();
        assertNotNull(ess);
    }

    @Test
    public void testGetDefinedStreamsIsValidBeforeStreamAccess() {
        EntitySamplerService ess = new RetailEntitySamplerService();
        List<SamplerDef> des = ess.getDefinedEntitySamplers();
        assertThat(des.size(),greaterThan(0));
    }

    @Test
    public void testGetSampleStreamMapIsEmptyBeforeLazyInit() throws Exception {
        EntitySamplerService ess = new RetailEntitySamplerService();
        Map<String, EntitySampler> ssmap = ess.getSampleStreamMap();
        assertThat(ssmap.values().size(),equalTo(0));
    }

    @Test
    public void testGetEntitySamplerReturnsWorkingSampler() {
        EntitySamplerService ess = new RetailEntitySamplerService();
        SamplerDef des = ess.getDefinedEntitySamplers().get(0);
        EntitySampler sampleStream = ess.getSampleStream(des.getSamplerName());
        EntitySample es;
        es = sampleStream.getNextEntity();
        Object[] vals = es.getFieldValues();
        assertThat(vals.length,greaterThan(0));
        Map<String, Object> vmap;
        vmap = es.getPrettyFieldValues();
        logger.info(vmap.toString());
    }

    @Test
    public void testBrandNames() {
        EntitySamplerService ess = new RetailEntitySamplerService();
        ess.getSampleStream("retail.brand");

        SamplerDef des = ess.getDefinedEntitySamplers().get(0);
        EntitySampler sampleStream = ess.getSampleStream(des.getSamplerName());
        EntitySample es = sampleStream.getNextEntity();
        Object[] vals = es.getFieldValues();
        assertThat(vals.length,greaterThan(0));
        Map<String, Object> vmap = es.getPrettyFieldValues();
        logger.info(vmap.toString());
    }




}