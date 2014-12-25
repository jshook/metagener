package com.metawiring;

import com.metawiring.coreapi.EntityDescriptor;
import com.metawiring.coreapi.FieldDescriptor;
import com.metawiring.coreapi.PopulationDescriptor;

import java.util.ArrayList;
import java.util.List;

public class MutableEntityDescriptor implements EntityDescriptor {
    private String name;
    private List<FieldDescriptor> fieldDescriptors = new ArrayList<FieldDescriptor>();
    private long populationSize = 10;

    public void setName() {
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

    public void addFieldDescriptor(FieldDescriptor fieldDescriptor) {
        this.fieldDescriptors.add(fieldDescriptor);
    }
    @Override
    public List<FieldDescriptor> getFieldDescriptors() {
        return fieldDescriptors;
    }

    public FieldDescriptor immutable() {
        return (FieldDescriptor) this;
    }
}
