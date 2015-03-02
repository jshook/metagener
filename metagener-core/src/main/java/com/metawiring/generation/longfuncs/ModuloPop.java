package com.metawiring.generation.longfuncs;

import com.metawiring.types.EntityDef;
import com.metawiring.types.functiontypes.EntityDefAware;
import com.metawiring.types.functiontypes.LongUnaryFieldFunction;

/**
 * Simple modulo by a number.
 */
public class ModuloPop implements LongUnaryFieldFunction, EntityDefAware {

    private long populationSize;

    @Override
    public long applyAsLong(long operand) {
        long result = operand % populationSize;
        return result;
    }

    @Override
    public void applyEntityDef(EntityDef entityDef) {
        this.populationSize = entityDef.getPopulationSize();
    }
}
