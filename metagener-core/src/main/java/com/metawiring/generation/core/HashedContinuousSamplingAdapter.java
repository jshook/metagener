package com.metawiring.generation.core;

import org.apache.commons.math3.distribution.RealDistribution;

import java.util.Arrays;

public class HashedContinuousSamplingAdapter {

    private RandomGeneratorAdapter randomMapper;
    private RealDistribution dist;

    public HashedContinuousSamplingAdapter(String... definition) {
        randomMapper = new RandomGeneratorAdapter();
        Class<? extends RealDistribution> distClass = SizedDistributionMapper.mapRealDistributionClass(definition[0]);
        dist = SizedDistributionMapper.mapRealDistribution(randomMapper, definition);
    }

    public double sample(long sampleId) {
        randomMapper.setSeed(sampleId);
        double sample = dist.sample();
        return sample;
    }

}
