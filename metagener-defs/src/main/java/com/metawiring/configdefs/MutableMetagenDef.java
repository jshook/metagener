package com.metawiring.configdefs;

import com.metawiring.types.EntityDef;
import com.metawiring.types.MetagenDef;
import com.metawiring.types.SamplerDef;

import java.util.ArrayList;
import java.util.List;

public class MutableMetagenDef implements MetagenDef {
    private List<EntityDef> entityDefs = new ArrayList<>();
    private List<SamplerDef> samplerDefs = new ArrayList<>();

    public void setEntityDefs(List<EntityDef> entityDefs) {
        this.entityDefs = entityDefs;
    }

    public void setSamplerDefs(List<SamplerDef> samplerDefs) {
        this.samplerDefs = samplerDefs;
    }

    public MetagenDef immutable() {
        return (MetagenDef) this;
    }

    @Override
    public List<EntityDef> getEntityDefs() {
        return entityDefs;
    }

    @Override
    public List<SamplerDef> getSamplerDefs() {
        return samplerDefs;
    }

    public void addSamplerDef(SamplerDef samplerDef) {
        samplerDefs.add(samplerDef);
    }

    public void addEntityDef(EntityDef entityDef) {
        entityDefs.add(entityDef);
    }
}
