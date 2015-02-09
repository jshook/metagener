package com.metawiring.webapi.resource;

import com.metawiring.types.EntitySample;
import com.metawiring.types.EntitySampler;
import com.metawiring.webapi.representation.SampleValue;
import com.metawiring.wiring.GenContext;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/sample/{samplername}/{sampleid}")
@Produces(MediaType.APPLICATION_JSON)
public class SampleResource {

    private GenContext metagenerContext;
    private static Long ONE = 1l;

    public SampleResource(GenContext metagenerContext) {
        this.metagenerContext = metagenerContext;
    }

    public SampleValue getSampleValue(
            @PathParam("samplername") String samplerName,
            @PathParam("sampleid") Optional<Long> optionalSampleId
    ) {
        long sampleId=optionalSampleId.orElse(ONE);
        EntitySampler entitySampleStream = metagenerContext.getEntitySampleStream(samplerName);
        if (entitySampleStream!=null) {
            EntitySample entitySample= entitySampleStream.getEntity(sampleId);
            return new SampleValue(sampleId,entitySample);
        }
        else {
            throw new RuntimeException("sampler [" + samplerName + "] was not found.");
        }
    }
}
