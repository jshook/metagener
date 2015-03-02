package com.metawiring.types;

import java.util.List;
import java.util.Map;

/**
 * A sample stream provider is a directory and access point for
 * a related set of sample streams.
 */
public interface EntitySamplerService {

    public Map<String,SamplerDef> getSampleStreamMap();

    /**
     * Get the named sample stream. This is more direct for clients
     * that know what they want.
     * @param name The name of the requested stream
     * @return The named stream
     * @throws com.metawiring.types.exceptions.SampleStreamException if the named stream doesn't exist
     */
    public EntitySampler getEntitySampleStream(String name);

    /**
     * Get the list of defined samplers. This may be different than the configured and running
     * entitySamplers, since these are initialized lazily.
     * @return The list of SamplerDefs.
     */
    public List<SamplerDef> getDefinedEntitySamplers();
}
