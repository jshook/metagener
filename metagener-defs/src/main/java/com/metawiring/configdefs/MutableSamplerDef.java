package com.metawiring.configdefs;

import com.metawiring.types.FuncDef;
import com.metawiring.types.SamplerDef;

public class MutableSamplerDef implements SamplerDef {
    private String entityName;
    private String samplerFunc;
    private String samplerName;
    private FuncDef samplerFuncDef;

    public MutableSamplerDef setEntityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public MutableSamplerDef setSamplerFunc(String samplerFunc) {
        this.samplerFunc = samplerFunc;
        return this;
    }

    public String getSamplerFunc() {
        return samplerFunc;
    }

    public MutableSamplerDef setSamplerName(String samplerName) {
        this.samplerName = samplerName;
        return this;
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


    public MutableSamplerDef setSamplerFuncDef(FuncDef samplerFuncDef) {
        this.samplerFuncDef = samplerFuncDef;
        return this;
    }

    public FuncDef getSamplerFuncDef() {
        return samplerFuncDef;
    }

    @Override
    public String toString() {
        return "MutableSamplerDef{" +
                "entityName='" + entityName + '\'' +
                ", samplerFunc='" + samplerFunc + '\'' +
                ", samplerName='" + samplerName + '\'' +
                ", samplerFuncDef=" + samplerFuncDef +
                '}';
    }
}
