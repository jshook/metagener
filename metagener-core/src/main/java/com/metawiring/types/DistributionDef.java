package com.metawiring.types;

/**
 * A configuration for how to draw samples from an addressable set of entities.
 */
public interface DistributionDef {
    public String getDistribution();
    public String getParameters();
}
