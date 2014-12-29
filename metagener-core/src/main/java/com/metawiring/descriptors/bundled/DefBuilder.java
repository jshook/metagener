package com.metawiring.descriptors.bundled;

import com.metawiring.config.MutableEntityDef;
import com.metawiring.config.MutableFieldDef;
import com.metawiring.config.MutableSamplerDef;
import com.metawiring.generation.FieldType;
import com.metawiring.types.ConfigDefs;
import com.metawiring.types.EntityDef;
import com.metawiring.types.SamplerDef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefBuilder implements ConfigDefs {
    private MutableEntityDef currentEntityDef;
    private MutableFieldDef currentFieldDef;
    private MutableSamplerDef currentSamplerDef;
    private Map<String,EntityDef> entityDefs = new HashMap<>();
    private Map<String,SamplerDef> samplerDefs = new HashMap<>();

    public DefBuilder entity(String s) {
        currentEntityDef = new MutableEntityDef();
        currentEntityDef.setName(s);
        entityDefs.put(s, currentEntityDef);
        return this;
    }

    public DefBuilder population(int i) {
        currentEntityDef.setPopulationSize(i);
        return this;
    }

    public DefBuilder field(String fieldName) {
        currentFieldDef = new MutableFieldDef();
        currentFieldDef.setFieldName(fieldName);
        currentEntityDef.addFieldDescriptor(currentFieldDef);
        return this;
    }

    public DefBuilder type(String fieldTypeName) {
        currentFieldDef.setFieldType(fieldTypeName);
        return this;
    }

    public DefBuilder generator(String generatorSpec) {
        currentFieldDef.setGenerator(generatorSpec);
        return this;
    }

    public DefBuilder sampleEntity(String samplerSpec) {
        currentSamplerDef = new MutableSamplerDef();
        currentSamplerDef.setEntityName(samplerSpec);
        return this;

    }

    public DefBuilder distribution(String distributionSpec) {
        currentSamplerDef.setDistributionSpec(distributionSpec);
        return this;
    }

    public DefBuilder as(String entitySamplerAlias) {
        currentSamplerDef.setName(entitySamplerAlias);
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
}
