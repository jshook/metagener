package com.metawiring.types;

/**
 * Some function configurations can come pre-bundled, ready-to-use.
 */
public interface PrebundledStreams {
    /**
     * return an instance of a SampleStream service which provides generated
     * data for retail streams.
     * @return a retail SampleStreamProvider
     */
    public EntitySamplerService getRetailStreams();
}
