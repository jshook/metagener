package com.metawiring.types.functiontypes;

import com.metawiring.types.EntityDef;

/**
 * If a function implements this interface, then it will be initialized with the associated entityDef.
 */
public interface EntityDefAware {
    /**
     * Provide the entityDef associated with the sampler which holds this function.
     * @param entityDef the owning EntityDef
     */
    public void applyEntityDef(EntityDef entityDef);
}
