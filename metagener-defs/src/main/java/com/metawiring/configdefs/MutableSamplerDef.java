package com.metawiring.configdefs;

import com.metawiring.types.SamplerDef;

public class MutableSamplerDef implements SamplerDef {
    private String entityName="";
    private String samplerFunc ="";
    private String samplerName ="";

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public void setSamplerFunc(String samplerFunc) {
        this.samplerFunc = samplerFunc;
    }

    public String getSamplerFunc() {
        return samplerFunc;
    }

    public void setSamplerName(String samplerName) {
        this.samplerName = samplerName;
    }

    public String getSamplerName() {
        return samplerName;
    }

    @Override
    public String getEntityName() {
        return entityName;
    }

    public SamplerDef immutable() {
        return (SamplerDef) this;
    }
}
