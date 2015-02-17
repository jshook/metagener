package com.metawiring.configdefs;

import com.metawiring.types.FieldDef;
import com.metawiring.types.FieldType;
import com.metawiring.types.FuncDef;

public class MutableFieldDef implements FieldDef {
    private String fieldName;
    private FieldType fieldType;
    private String fieldFunc;
    private FuncDef fieldFuncDef;

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

    public void setFieldFunc(String function) {
        this.fieldFunc = function;
    }

    public String getFieldFunc() {
        return fieldFunc;
    }

    public FuncDef getFieldFuncDef() {
        return fieldFuncDef;
    }

    public void setFieldFuncDef(FuncDef fieldFuncDef) {
        this.fieldFuncDef = fieldFuncDef;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(fieldName).append(":").append(fieldType);
        sb.append(" <- ").append(fieldFuncDef.toString());
        return sb.toString();
    }
}
