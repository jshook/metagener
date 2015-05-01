package com.metawiring.types.functiontypes;

import com.metawiring.types.EntitySampler;

/**
 * <p>
 * If a function implements this interface, then it will be initialized with the associated entitySampler. This
 * allows functions to make calls back through the entity sampler instance.
 * </p>
 *
 * <p>
 *     The associated entity sampler will not be expected to be callable until all functions are initialized. Do not
 *     attempt to call them during construction or initialization methods.
 * </p>
 */
public interface EntitySamplerAware {

    void applyEntitySampler(EntitySampler entitySampler);

}
