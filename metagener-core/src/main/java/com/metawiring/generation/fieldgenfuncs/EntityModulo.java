package com.metawiring.generation.fieldgenfuncs;

import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.EntityDefAware;
import com.metawiring.types.functiontypes.LongUnaryFieldFunction;
import com.metawiring.types.EntityDef;

/**
 * Remap the input long to an entity id within the population.
 */
public class EntityModulo implements LongUnaryFieldFunction, EntityDefAware {

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
