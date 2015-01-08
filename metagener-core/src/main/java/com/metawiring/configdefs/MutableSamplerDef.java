package com.metawiring.configdefs;

import com.metawiring.types.SamplerDef;

public class MutableSamplerDef implements SamplerDef {
    private String entityName="";
    private String distributionSpec="";
    private String name="";

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public void setDistributionSpec(String distributionSpec) {
        this.distributionSpec = distributionSpec;
    }

    public String getDistributionSpec() {
        return distributionSpec;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getEntityName() {
        return entityName;
    }

    public SamplerDef immutable() {
        return (SamplerDef) this;
    }
}
