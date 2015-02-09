package com.metawiring.configdefs;

import com.metawiring.types.EntityDef;
import com.metawiring.types.FieldDef;
import com.metawiring.types.FuncDef;

import java.util.*;

public class MutableEntityDef implements EntityDef {
    private String name;
    private List<FieldDef> fieldDefs = new ArrayList<FieldDef>();
    private List<FuncDef> funcDefs = new ArrayList<>();
    private Map<String,FieldDef> fieldDefMap = new LinkedHashMap<String, FieldDef>();
    private Map<String,FuncDef> funcDefMap = new LinkedHashMap<String,FuncDef>();

    private long populationSize = Long.MAX_VALUE;

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
    public MutableEntityDef addFuncDef(FuncDef funcDef) {
        this.funcDefMap.put(funcDef.getFuncName(), funcDef);
        this.funcDefs.add(funcDef);
        return this;
    }

    public EntityDef immutable() {
        return (EntityDef) this;
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

    @Override
    public FuncDef getFuncDef(String funcName) {
        return funcDefMap.get(funcName);
    }

    @Override
    public List<FuncDef> getFuncDefs() {
        return funcDefs;
    }

}
