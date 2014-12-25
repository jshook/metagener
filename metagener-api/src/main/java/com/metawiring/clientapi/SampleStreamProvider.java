package com.metawiring.clientapi;

import java.util.Map;

/**
 * A sample stream provider is a directory and access point for
 * a related set of sample streams.
 */
public interface SampleStreamProvider {

    /**
     * Get a map of all the sample streams, by name. This is useful
     * for interrogating the streams available and their metadata.
     * @return sample stream map
     */
    public Map<String,EntitySampleStream> getSampleStreamMap();

    /**
     * Get the named sample stream. This is more direct for clients
     * that know what they want.
     * @param name The name of the requested stream
     * @return The named stream
     * @throws com.metawiring.coreapi.SampleStreamException if the named stream doesn't exist
     */
    public EntitySampleStream getSampleStream(String name);
}
