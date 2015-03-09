package com.metawiring.configdefs;

import com.metawiring.types.FieldDef;
import com.metawiring.types.FieldType;
import com.metawiring.types.FuncDef;

public class MutableFieldDef implements FieldDef {
    private String fieldName;
    private FieldType fieldType;
    private String fieldFunc;
    private FuncDef fieldFuncDef;

    public MutableFieldDef setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }


    public MutableFieldDef setFieldType(String fieldTypeName) {
        this.fieldType = FieldType.typeOf(fieldTypeName);
        return this;
    }

    @Override
    public FieldType getFieldType() {
        return fieldType;
    }

    public FieldDef immutable() {
        return (FieldDef) this;
    }

    public MutableFieldDef setFieldFunc(String function) {
        this.fieldFunc = function;
        return this;
    }

    public String getFieldFunc() {
        return fieldFunc;
    }

    public FuncDef getFieldFuncDef() {
        return fieldFuncDef;
    }

    public MutableFieldDef setFieldFuncDef(FuncDef fieldFuncDef) {
        this.fieldFuncDef = fieldFuncDef;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(fieldName).append(":").append(fieldType);
        sb.append(" <- ").append(fieldFuncDef.toString());
        return sb.toString();
    }
}
