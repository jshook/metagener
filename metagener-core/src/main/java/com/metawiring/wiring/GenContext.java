package com.metawiring.wiring;

import com.metawiring.defbuilder.DefBuilderTypes;
import com.metawiring.defbuilder.GenContextBuilder;
import com.metawiring.types.MetagenDef;
import com.metawiring.types.EntitySampler;
import com.metawiring.types.EntityDef;
import com.metawiring.types.SamplerDef;
import com.metawiring.generation.core.EntitySamplerImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * You should only have one function context per app
 */
public class GenContext {

    // The configured generator context
    private MetagenDef metagenDef;

    // The map of sampler implementations
    private Map<String, EntitySampler> entitySamplerMap = new HashMap<>();

    // Entities, defined
    private Map<String,EntityDef> entityDefMap = new HashMap<>();

    // Samplers, defined
    private Map<String,SamplerDef> samplerDefMap = new HashMap<>();

    public GenContext(MetagenDef metagenDef) {
        loadDefs(metagenDef);
    }


    public EntitySampler getEntitySampleStream(String entitySampleStreamName) {
        EntitySampler ess = entitySamplerMap.get(entitySampleStreamName);
        if (ess==null) {
            synchronized (this) {
                ess = entitySamplerMap.get(entitySampleStreamName);
                if (ess==null) {
                    ess = createEntitySampler(entitySampleStreamName);
                    entitySamplerMap.put(entitySampleStreamName,ess);
                }
            }
        }
        return ess;

    }

    private EntitySampler createEntitySampler(String entitySampleStreamName) {
        SamplerDef essd = samplerDefMap.get(entitySampleStreamName);
        if (essd==null) {
            throw new RuntimeException("Undefined samplerDef [" + entitySampleStreamName + "]");
        }
        EntityDef entityDef = entityDefMap.get(essd.getEntityName());
        if (entityDef==null) {
            throw new RuntimeException("Undefined entityDef [" + essd.getEntityName() + "]");
        }

        EntitySamplerImpl sampleStream = new EntitySamplerImpl(essd,entityDef);
        sampleStream.resolvePipeline();
        return sampleStream;
    }

    public GenContext loadDefs(MetagenDef cb) {
        loadEntityDefs(cb.getEntityDefs());
        loadSamplerDefs(cb.getSamplerDefs());
        return this;
    }

    private void loadEntityDefs(List<EntityDef> entityDefs) {
        for (EntityDef entityDef : entityDefs) {
            this.entityDefMap.put(entityDef.getName(), entityDef);
        }
    }

    private void loadSamplerDefs(List<SamplerDef> samplerDefs) {
        for (SamplerDef samplerDef : samplerDefs) {
            this.samplerDefMap.put(samplerDef.getSamplerName(), samplerDef);
        }
    }

    public Map<? extends String, ? extends EntitySampler> getEntitySamplerMap() {
        return entitySamplerMap;
    }

    public List<SamplerDef> getDefinedEntitySamplers() {
        return new ArrayList<SamplerDef>(samplerDefMap.values());
    }

    public static DefBuilderTypes builder() {
        return GenContextBuilder.builder();
    }

    public MetagenDef getMetagenDef() {
        return metagenDef;
    }
}
