package com.metawiring.clientapi;

public interface PrebundledStreams {
    /**
     * return an instance of a SampleStream service which provides generated
     * data for retail streams.
     * @return a retail SampleStreamProvider
     */
    public SampleStreamProvider getRetailStreams();
}
