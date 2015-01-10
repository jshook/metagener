package com.metawiring.defbuilder;

import com.metawiring.configdefs.MutableEntityDef;
import com.metawiring.configdefs.MutableFieldDef;
import com.metawiring.configdefs.MutableSamplerDef;
import com.metawiring.types.ConfigDefs;
import com.metawiring.types.EntityDef;
import com.metawiring.types.SamplerDef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContextBuilder implements ConfigDefs, DefBuilderTypes {
    private MutableEntityDef currentEntityDef;
    private MutableFieldDef currentFieldDef;
    private MutableSamplerDef currentSamplerDef;
    private Map<String,EntityDef> entityDefs = new HashMap<>();
    private Map<String,SamplerDef> samplerDefs = new HashMap<>();

    private ContextBuilder() {
    }

    public static DefBuilderTypes builder() {
        return new ContextBuilder();
    }

    public ContextBuilder population(int i) {
        currentEntityDef.setPopulationSize(i);
        return this;
    }

    public ContextBuilder field(String fieldName) {
        currentFieldDef = new MutableFieldDef();
        currentFieldDef.setFieldName(fieldName);
        currentEntityDef.addFieldDescriptor(currentFieldDef.immutable());
        return this;
    }

    public ContextBuilder type(String fieldTypeName) {
        currentFieldDef.setFieldType(fieldTypeName);
        return this;
    }

    public ContextBuilder function(String generatorSpec) {
        currentFieldDef.setFunction(generatorSpec);
        return this;
    }


    public SamplerDefBuilderTypes.wantsSamplerFunction sampler(String entityName, String samplerName) {
        currentSamplerDef = new MutableSamplerDef();
        currentSamplerDef.setSamplerName(samplerName);
        currentSamplerDef.setEntityName(entityName);
        samplerDefs.put(currentSamplerDef.getSamplerName(),currentSamplerDef);
        return this;
    }

    public SamplerDefBuilderTypes.wantsSamplerFunction sampler(String entityName) {
        currentSamplerDef = new MutableSamplerDef();
        currentSamplerDef.setSamplerName(entityName);
        currentSamplerDef.setEntityName(entityName);
        samplerDefs.put(currentSamplerDef.getSamplerName(),currentSamplerDef);
        return this;
    }

    public ContextBuilder samplerFunction(String distributionSpec) {
        currentSamplerDef.setSamplerFunction(distributionSpec);
        return this;
    }

    public ContextBuilder as(String entitySamplerAlias) {
        currentSamplerDef.setSamplerName(entitySamplerAlias);
        samplerDefs.put(entitySamplerAlias,(SamplerDef) currentSamplerDef);
        return this;
    }

    @Override
    public List<EntityDef> getEntityDefs() {
        return new ArrayList<EntityDef>(entityDefs.values());
    }

    @Override
    public List<SamplerDef> getSamplerDefs() {
        return new ArrayList<SamplerDef>(samplerDefs.values());
    }

    public EntityBuilderTypes.wantsEntityPop entity(String entityName) {
        currentEntityDef = new MutableEntityDef();
        currentEntityDef.setName(entityName);
        entityDefs.put(entityName, currentEntityDef);
        return this;
    }

    @Override
    public SamplerDefBuilderTypes.wantsSamplerDefs entityFunction(String entitySamplerFunction) {
        currentSamplerDef.setSamplerFunction(entitySamplerFunction);
        return this;
    }
}
