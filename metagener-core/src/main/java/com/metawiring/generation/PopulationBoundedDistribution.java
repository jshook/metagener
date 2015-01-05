package com.metawiring.generation;

import com.metawiring.types.EntityDefAware;

/**
 * Implement this to reduce a pseudo random long value to a discrete sample of a known population
 */
public interface PopulationBoundedDistribution extends EntityGeneratorFunction, EntityDefAware {
}
