package com.metawiring.coreapi;

/**
 * A configuration for how to draw samples from an addressable set of entities.
 */
public interface SamplingDescriptor {
    public String getDistribution();
    public String getParameters();
}
