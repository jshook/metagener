package com.metawiring.wiring;

import com.metawiring.types.ConfigDefs;
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
public class GeneratorContext {

    // The map of sampler implementations
    private Map<String, EntitySampler> entitySamplerMap = new HashMap<>();

    // Entities, defined
    private Map<String,EntityDef> entityDefMap = new HashMap<>();

    // Samplers, defined
    private Map<String,SamplerDef> samplerDefMap = new HashMap<>();



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
            throw new RuntimeException("attempt to access undefined samplerDef [" + entitySampleStreamName + "]");
        }
        EntityDef entityDef = entityDefMap.get(essd.getEntityName());
        if (entityDef==null) {
            throw new RuntimeException("attempt to access undefined entityDef [" + essd.getEntityName() + "]");
        }

        EntitySamplerImpl sampleStream = new EntitySamplerImpl(essd,entityDef);
        sampleStream.resolvePipeline();
        return sampleStream;
    }

    public void loadDefs(ConfigDefs cb) {
        loadEntityDefs(cb.getEntityDefs());
        loadSamplerDefs(cb.getSamplerDefs());
    }

    private void loadEntityDefs(List<EntityDef> entityDefs) {
        for (EntityDef entityDef : entityDefs) {
            this.entityDefMap.put(entityDef.getName(), entityDef);
        }
    }

    private void loadSamplerDefs(List<SamplerDef> samplerDefs) {
        for (SamplerDef samplerDef : samplerDefs) {
            this.samplerDefMap.put(samplerDef.getName(), samplerDef);
        }
    }

    public Map<? extends String, ? extends EntitySampler> getEntitySamplerMap() {
        return entitySamplerMap;
    }

    public List<SamplerDef> getDefinedEntitySamplers() {
        return new ArrayList<SamplerDef>(samplerDefMap.values());
    }
}
