package com.metawiring.types;

import com.metawiring.generation.core.UNUSEDMetagenerContext;

public interface EntitySamplerFactory {
    EntitySampler compose(UNUSEDMetagenerContext UNUSEDMetagenerContext, SamplerDef sd, EntityDef ed);
}
