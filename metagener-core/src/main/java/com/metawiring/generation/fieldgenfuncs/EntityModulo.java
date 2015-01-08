package com.metawiring.generation.fieldgenfuncs;

import com.metawiring.types.functiontypes.EntityDefAware;
import com.metawiring.types.functiontypes.LongFieldFunction;
import com.metawiring.types.EntityDef;

/**
 * Remap the input long to an entity id within the population.
 */
public class EntityModulo implements LongFieldFunction, EntityDefAware {

    private long moduloBy;

    /**
     * Remap the input long to an entity id within the population.
     *
     * @param operand the input, probably a hashed long
     * @return A long value within the population range [inclusive,exclusive)
     */
    @Override
    public long applyAsLong(long operand) {
        return operand % moduloBy;
    }

    @Override
    public void applyEntityDef(EntityDef entityDef) {
        this.moduloBy = entityDef.getPopulationSize();
    }

}
