package com.metawiring.generation.valuemapfunctions;

import com.metawiring.annotations.FieldFunctionSignature;
import com.metawiring.generation.FieldFunction;
import com.metawiring.types.EntityDef;
import com.metawiring.types.EntityDefAware;

/**
 * Remap the input long to an entity id within the population.
 */
@FieldFunctionSignature(input=Long.class,output = Long.class)
public class EntityModulo implements FieldFunction<Long,Long>, EntityDefAware {

    private long moduloBy;

    /**
     * Remap the input long to an entity id within the population.
     * @param aLong the input, probably a hashed long
     * @return A long value within the population range [inclusive,exclusive)
     */
    @Override
    public Long apply(Long aLong) {
        return (aLong % moduloBy);
   }

    @Override
    public void applyEntityDef(EntityDef entityDef) {
        this.moduloBy = moduloBy;
    }
}
