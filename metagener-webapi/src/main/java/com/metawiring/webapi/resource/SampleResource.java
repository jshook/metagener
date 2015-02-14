package com.metawiring.webapi.resource;

import com.metawiring.types.EntitySample;
import com.metawiring.types.EntitySampler;
import com.metawiring.webapi.representation.SampleValue;
import com.metawiring.wiring.GenContext;
import com.metawiring.wiring.GenContexts;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/sample")
public class SampleResource {

    private GenContexts metagenerContexts;
    private static Long ONE = 1l;

    public SampleResource(GenContexts metagenerContexts) {
        this.metagenerContexts = metagenerContexts;
    }

    @GET
    @Path("{contextName}/{samplername}")
    @Produces(MediaType.APPLICATION_JSON)
    public SampleValue getNextSampleValue(
            @PathParam("contextName") String contextName,
            @PathParam("samplername") String samplerName
    ) {
        GenContext genContext = metagenerContexts.get(contextName);
        if (genContext==null) {
            throw new NotFoundException("Undefined generator context:" + contextName);
        }

        EntitySampler entitySampleStream = genContext.getEntitySampleStream(samplerName);
        if (entitySampleStream==null) {
            throw new NotFoundException("Undefined sampler: context=" + contextName + ", sampler=" + samplerName);
        }

        EntitySample entitySample= entitySampleStream.getNextEntity();

        return new SampleValue(entitySample);
    }

    @GET
    @Path("{contextName}/{samplername}/{sampleid}")
    @Produces(MediaType.APPLICATION_JSON)
    public SampleValue getSampleValue(
            @PathParam("contextName") String contextName,
            @PathParam("samplername") String samplerName,
            @PathParam("sampleid") String optionalSampleId
    ) {
        String sampleIdString= optionalSampleId!=null ? optionalSampleId : "1";
        long sampleId = Long.valueOf(sampleIdString);

        GenContext genContext = metagenerContexts.get(contextName);
        if (genContext==null) {
            throw new NotFoundException("Undefined generator context:" + contextName);
        }

        EntitySampler entitySampleStream = genContext.getEntitySampleStream(samplerName);
        if (entitySampleStream==null) {
            throw new NotFoundException("Undefined sampler: context=" + contextName + ", sampler=" + samplerName);
        }

        EntitySample entitySample= entitySampleStream.getEntity(sampleId);

        return new SampleValue(entitySample);

    }
}

