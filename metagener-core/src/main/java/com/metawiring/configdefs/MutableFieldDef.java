package com.metawiring.configdefs;

import com.metawiring.generation.core.FieldType;
import com.metawiring.types.FieldDef;

public class MutableFieldDef implements FieldDef {
    private String fieldName;
    private FieldType fieldType;
    private String function;

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

    public void setFunction(String function) {
        this.function = function;
    }

    public String getFunction() {
        return function;
    }
}
