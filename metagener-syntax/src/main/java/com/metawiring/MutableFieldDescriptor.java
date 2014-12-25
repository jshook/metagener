package com.metawiring;

import com.metawiring.coreapi.FieldDescriptor;

public class MutableFieldDescriptor implements FieldDescriptor {
    private String fieldName;
    private String softType;
    private Class<?> classType;

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    @Override
    public String getFieldName() {
        return fieldName;
    }

    public void setSoftType(String softType) {
        this.softType = softType;
    }
    @Override
    public String getSoftType() {
        return softType;
    }

    public void setClassType(Class<?> classType) {
        this.classType = classType;
    }
    @Override
    public Class<?> getValueClass() {
        return classType;
    }

    public FieldDescriptor immutable() {
        return (FieldDescriptor) this;
    }


}
