package com.metawiring.generation.core;

import com.metawiring.types.EntityDef;
import com.metawiring.types.EntitySampler;
import com.metawiring.types.EntitySamplerFactory;
import com.metawiring.types.SamplerDef;
import com.metawiring.types.exceptions.SampleStreamException;

import java.util.HashMap;
import java.util.Map;

public class UNUSEDMetagenerContext {

    private Map<String,EntityDef> entityDescriptorMap = new HashMap<>();
    private Map<String,SamplerDef> samplerDescriptorMap = new HashMap<>();
    private Map<String,EntitySampler> entitySamplerMap = new HashMap<>();

    private EntitySamplerFactory entitySamplerFactory = new DefaultEntitySamplerFactory();

    public UNUSEDMetagenerContext addEntityDef(EntityDef entityDef) {
        String entityName = entityDef.getName();
        if (entityDescriptorMap.containsKey(entityName)) {
            throw new SampleStreamException("entity definition " + entityName + " is already defined");
        }
        entityDescriptorMap.put(entityName, entityDef);
        return this;
    }

    public UNUSEDMetagenerContext addSamplerDef(SamplerDef samplerDef) {
        String sampleName = samplerDef.getSamplerName();
        if (samplerDescriptorMap.containsKey(sampleName)) {
            throw new SampleStreamException("sampler definition " + sampleName + " is already defined");
        }
        return this;
    }

    public EntitySampler getSampler(String entitySamplerName) {
        EntitySampler es;
        es = entitySamplerMap.get(entitySamplerName);
        if (es==null) {
            synchronized(this) {
                es = entitySamplerMap.get(entitySamplerName);
                if (es==null) {

                    SamplerDef sd = samplerDescriptorMap.get(entitySamplerName);
                    if (sd == null ) {
                        throw new SampleStreamException("could not compose sample stream for " + entitySamplerName + ": missing samplerDef");
                    }
                    EntityDef ed = entityDescriptorMap.get(sd.getEntityName());
                    if (ed == null ) {
                        throw new SampleStreamException("could not compose sample stream for " + entitySamplerName + ": missing entityDef");
                    }

                    es = composeEntitySampler(sd,ed);
                    entitySamplerMap.put(sd.getSamplerName(),es);
                }
            }
        }
        return es;
    }

    private EntitySampler composeEntitySampler(SamplerDef sd,EntityDef ed) {
        EntitySampler es = entitySamplerFactory.compose(this,sd,ed);
        return es;
    }

}
