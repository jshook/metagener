package com.metawiring.config;

import com.metawiring.generation.FieldType;
import com.metawiring.types.FieldDef;

public class MutableFieldDef implements FieldDef {
    private String fieldName;
    private FieldType fieldType;
    private String generator;

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }


    public void setFieldType(String fieldTypeName) {
        this.fieldType = FieldType.typeOf(fieldTypeName);
    }

    @Override
    public FieldType getFieldType() {
        return fieldType;
    }

    public FieldDef asImmutable() {
        return (FieldDef) this;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public String getGenerator() {
        return generator;
    }
}
