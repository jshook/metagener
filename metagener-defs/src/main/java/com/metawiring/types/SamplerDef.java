package com.metawiring.types;

import java.util.List;

/**
 * This defines the connecting point between named entity definitions and samplers of those entity definitions.
 * It represents the configuration which is the result of combining them pair-wise.
 */
public interface SamplerDef {

    /**
     * The name of the sampler, possibly different than the name of the entity it samples
     * @return sampler name
     */
    public String getSamplerName();

    /**
     * The name of the entity that this sampler is intended to sample from. The population of this
     * entity will be used to constrain the valid sample values.
     * @return the name of the entity to sample
     */
    public String getEntityName();

    /**
     * The statistical samplerFunction name and parameters used to sample values from this population
     * @return the samplerFunction specification
     */
    public FuncDef getSamplerFuncDef();
}
