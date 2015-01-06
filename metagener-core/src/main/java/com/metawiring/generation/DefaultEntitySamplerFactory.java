package com.metawiring.generation;

import com.metawiring.types.EntityDef;
import com.metawiring.types.EntitySampler;
import com.metawiring.types.SamplerDef;

public class DefaultEntitySamplerFactory implements EntitySamplerFactory {
    @Override
    public EntitySampler compose(GeneratorContext generatorContext, SamplerDef sd, EntityDef ed) {
        return new EntitySamplerImpl(sd,ed);
    }
}
