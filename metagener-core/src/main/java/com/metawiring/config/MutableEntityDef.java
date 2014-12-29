package com.metawiring.config;

import com.metawiring.types.EntityDef;
import com.metawiring.types.FieldDef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MutableEntityDef implements EntityDef {
    private String name;
    private List<FieldDef> fieldDefs = new ArrayList<FieldDef>();
    private Map<String,FieldDef> fieldDefMap = new HashMap<>();

    private long populationSize = 10;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setPopulationSize(long populationSize) {
        this.populationSize = populationSize;
    }
    @Override
    public long getPopulationSize() {
        return populationSize;
    }

    public void addFieldDescriptor(FieldDef fieldDef) {
        this.fieldDefMap.put(fieldDef.getFieldName(),fieldDef);
        this.fieldDefs.add(fieldDef);
    }

    @Override
    public List<FieldDef> getFieldDefs() {
        return fieldDefs;
    }

    @Override
    public FieldDef getFieldDef(String fieldName) {
        return fieldDefMap.get(fieldName);
    }

    public FieldDef immutable() {
        return (FieldDef) this;
    }
}
