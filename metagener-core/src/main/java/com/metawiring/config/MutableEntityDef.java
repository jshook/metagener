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

    public MutableEntityDef setName(String name) {
        this.name = name;
        return this;
    }
    public MutableEntityDef setPopulationSize(long populationSize) {
        this.populationSize = populationSize;
        return this;
    }
    public MutableEntityDef addFieldDescriptor(FieldDef fieldDef) {
        this.fieldDefMap.put(fieldDef.getFieldName(),fieldDef);
        this.fieldDefs.add(fieldDef);
        return this;
    }

    public FieldDef immutable() {
        return (FieldDef) this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getPopulationSize() {
        return populationSize;
    }

    @Override
    public List<FieldDef> getFieldDefs() {
        return fieldDefs;
    }

    @Override
    public FieldDef getFieldDef(String fieldName) {
        return fieldDefMap.get(fieldName);
    }

}
