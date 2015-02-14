package com.metawiring.syntax;

import com.metawiring.types.EntityDef;
import com.metawiring.types.MetagenDef;
import com.metawiring.types.SamplerDef;

public class MetagenDefStringer {

    public static String toSyntax(MetagenDef metagenDef) {
        StringBuilder sb = new StringBuilder();
        for (EntityDef entityDef : metagenDef.getEntityDefs()) {
            sb.append("entity ").append(entityDef.getName()).append("\n");
        }
        for (SamplerDef samplerDef : metagenDef.getSamplerDefs()) {
            sb.append("sampler").append(samplerDef.getSamplerName()).append("\n");
        }
        return sb.toString();
    }
}
