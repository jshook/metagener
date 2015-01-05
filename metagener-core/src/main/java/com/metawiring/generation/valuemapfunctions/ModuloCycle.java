package com.metawiring.generation.valuemapfunctions;

import com.metawiring.types.EntityDef;
import com.metawiring.types.EntityDefAware;
import com.metawiring.types.EntityHashFunction;

public class ModuloCycle implements EntityHashFunction, EntityDefAware {

    private EntityDef entityDef;

    @Override
    public long applyAsLong(long operand) {
        return operand % entityDef.getPopulationSize();
    }

    @Override
    public void applyEntityDef(EntityDef entityDef) {
        this.entityDef = entityDef;

    }
}
