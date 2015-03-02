package com.metawiring.descriptors.bundled;

import com.metawiring.types.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
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
        PrebundledStreams pbs = new PrebundledStreams();
        EntitySamplerService ess  = pbs.getRetailStreams();
        assertNotNull(ess);
    }

    @Test
    public void testGetDefinedStreamsIsValidBeforeStreamAccess() {
        EntitySamplerService ess = new RetailEntitySamplerService();
        List<SamplerDef> des = ess.getDefinedEntitySamplers();
        assertThat(des.size(),greaterThan(0));
    }

    @Test(enabled=false)
    public void testGetEntitySamplerReturnsWorkingSampler() {
        EntitySamplerService ess = new RetailEntitySamplerService();
        SamplerDef des = ess.getDefinedEntitySamplers().get(0);
        EntitySampler sampleStream = ess.getEntitySampleStream("brand");
        EntitySample es;
        for (int i=0; i<10; i++) {
            es = sampleStream.getNextEntity();
            Object[] vals = es.getFieldValues();
            assertThat(vals.length,greaterThan(0));
            Map<String, Object> vmap;
            vmap = es.getOrderedFieldMap();
            logger.info(vmap.toString());
        }
    }

    @Test(enabled = false)
    public void testBrandNames() {
        EntitySamplerService ess = new RetailEntitySamplerService();
        ess.getEntitySampleStream("brand");

        SamplerDef des = ess.getDefinedEntitySamplers().get(0);
        EntitySampler sampleStream = ess.getEntitySampleStream(des.getSamplerName());
        EntitySample es = sampleStream.getNextEntity();
        Object[] vals = es.getFieldValues();
        assertThat(vals.length,greaterThan(0));
        Map<String, Object> vmap = es.getOrderedFieldMap();
        logger.info(vmap.toString());
    }


    @Test
    public void testEntitySamplersAreValid() {
        EntitySamplerService retail = new RetailEntitySamplerService();
        retail.getEntitySampleStream("retail.employees");
        retail.getEntitySampleStream("retail.stores");
        retail.getEntitySampleStream("retail.item_scans");
    }

    @Test
    public void testEntitySamplerValues() {
        EntitySamplerService retail = new RetailEntitySamplerService();
        EntitySampler employees = retail.getEntitySampleStream("retail.employees");
        EntitySampler stores = retail.getEntitySampleStream("retail.stores");
        EntitySampler item_scans = retail.getEntitySampleStream("retail.item_scans");

        EntitySample nextEntity;
        for (int i = 0; i < 10; i++) {
            nextEntity = employees.getNextEntity();
            System.out.println(nextEntity.toString());
        }

    }

}