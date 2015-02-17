package com.metawiring.webapi.resource;

import com.metawiring.types.EntitySample;
import com.metawiring.types.EntitySampler;
import com.metawiring.webapi.representation.BulkSampleValues;
import com.metawiring.webapi.representation.SampleValue;
import com.metawiring.wiring.GenContext;
import com.metawiring.wiring.GenContexts;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/bulksample")
public class BulkSampleResource {

    private GenContexts metagenerContexts;
    private static Long ONE = 1l;

    public BulkSampleResource(GenContexts metagenerContexts) {
        this.metagenerContexts = metagenerContexts;
    }

    @GET
    @Path("{contextName}/{samplername}/{count}")
    @Produces(MediaType.APPLICATION_JSON)
    public BulkSampleValues getNextSampleValue(
            @PathParam("contextName") String contextName,
            @PathParam("samplername") String samplerName,
            @PathParam("count") int count
    ) {
        GenContext genContext = metagenerContexts.get(contextName);
        if (genContext==null) {
            throw new NotFoundException("Undefined generator context:" + contextName);
        }

        EntitySampler entitySampleStream = genContext.getEntitySampleStream(samplerName);
        if (entitySampleStream==null) {
            throw new NotFoundException("Undefined sampler: context=" + contextName + ", sampler=" + samplerName);
        }

        List<SampleValue> sampleValues = new ArrayList<SampleValue>();
        for (int idx=0;idx<count;idx++) {
            EntitySample nextEntity = entitySampleStream.getNextEntity();
            sampleValues.add(new SampleValue(nextEntity));
        }
        long initialId=sampleValues.get(0).getSampleId();

        return new BulkSampleValues(initialId, initialId+count-1, sampleValues);
    }

}

