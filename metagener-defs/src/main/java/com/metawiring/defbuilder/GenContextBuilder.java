package com.metawiring.defbuilder;

import com.metawiring.configdefs.MutableEntityDef;
import com.metawiring.configdefs.MutableFieldDef;
import com.metawiring.configdefs.MutableMetagenDef;
import com.metawiring.configdefs.MutableSamplerDef;
import com.metawiring.types.MetagenDef;

public class GenContextBuilder implements DefBuilderTypes {
    private MutableMetagenDef mutableMetagenDef;
    private MutableEntityDef currentEntityDef;
    private MutableFieldDef currentFieldDef;
    private MutableSamplerDef currentSamplerDef;
//    private Map<String,EntityDef> entityDefs = new HashMap<>();
//    private Map<String,SamplerDef> samplerDefs = new HashMap<>();

    private GenContextBuilder() {
        mutableMetagenDef = new MutableMetagenDef();
    }

    public static DefBuilderTypes builder() {
        return new GenContextBuilder();
    }

    public GenContextBuilder population(int i) {
        currentEntityDef.setPopulationSize(i);
        return this;
    }

    public GenContextBuilder field(String fieldName) {
        currentFieldDef = new MutableFieldDef();
        currentFieldDef.setFieldName(fieldName);
        currentEntityDef.addFieldDescriptor(currentFieldDef.immutable());
        return this;
    }

    public GenContextBuilder type(String fieldTypeName) {
        currentFieldDef.setFieldType(fieldTypeName);
        return this;
    }

    public GenContextBuilder function(String generatorSpec) {
        currentFieldDef.setFunction(generatorSpec);
        return this;
    }


    public SamplerDefBuilderTypes.wantsSamplerFunction sampler(String entityName, String samplerName) {
        currentSamplerDef = new MutableSamplerDef();
        currentSamplerDef.setEntityName(entityName);
        currentSamplerDef.setSamplerName(samplerName);
        mutableMetagenDef.addSamplerDef(currentSamplerDef.immutable());
        return this;
    }

    public SamplerDefBuilderTypes.wantsSamplerFunction sampler(String entityName) {
        currentSamplerDef = new MutableSamplerDef();
        currentSamplerDef.setEntityName(entityName);
        currentSamplerDef.setSamplerName(entityName);
        mutableMetagenDef.addSamplerDef(currentSamplerDef.immutable());
        return this;
    }

    public GenContextBuilder samplerFunction(String distributionSpec) {
        currentSamplerDef.setSamplerFunc(distributionSpec);
        return this;
    }

    public GenContextBuilder as(String entitySamplerAlias) {
        currentSamplerDef.setSamplerName(entitySamplerAlias);
        return this;
    }

    public EntityBuilderTypes.wantsEntityPop entity(String entityName) {
        currentEntityDef = new MutableEntityDef();
        currentEntityDef.setName(entityName);
        mutableMetagenDef.addEntityDef(currentEntityDef.immutable());
        return this;
    }

    public SamplerDefBuilderTypes.wantsSamplerDefs entityFunction(String entitySamplerFunction) {
        currentSamplerDef.setSamplerFunc(entitySamplerFunction);
        return this;
    }

    public MetagenDef build() {
        return mutableMetagenDef.immutable();
    }
}
