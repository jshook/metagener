package com.metawiring.types.functiontypes;

import com.metawiring.types.SamplerDef;

/**
 * If a function implements this interface, then it will be initialized with the associated SamplerDef
 */
public interface SamplerDefAware {
    /**
     * Provide the SamplerDef for the sampler which holds this function.
     * @param samplerDef the owning SamplerDef
     */
    public void applySamplerDef(SamplerDef samplerDef);
}
