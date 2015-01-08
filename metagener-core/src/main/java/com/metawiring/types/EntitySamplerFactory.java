package com.metawiring.types;

import com.metawiring.generation.core.GeneratorContext;
import com.metawiring.types.EntityDef;
import com.metawiring.types.EntitySampler;
import com.metawiring.types.SamplerDef;

public interface EntitySamplerFactory {
    EntitySampler compose(GeneratorContext generatorContext, SamplerDef sd, EntityDef ed);
}
