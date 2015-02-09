package com.metawiring.configdefs;

import com.metawiring.types.FieldDef;
import com.metawiring.types.FieldType;

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

    public FieldDef immutable() {
        return (FieldDef) this;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getFunction() {
        return function;
    }
}
