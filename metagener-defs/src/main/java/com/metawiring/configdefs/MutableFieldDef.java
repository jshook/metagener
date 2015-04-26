package com.metawiring.configdefs;

import com.metawiring.types.FieldDef;
import com.metawiring.types.FieldType;
import com.metawiring.types.FuncDef;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.stream.Collectors;

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
        try {
            this.fieldType = FieldType.typeOf(fieldTypeName);
        } catch (Exception e) {
            throw new InvalidParameterException("field type '" + fieldTypeName + "' is not a known type. Did you mean one of these?: "
                    + Arrays.asList(FieldType.values()).stream().map(Enum::toString).collect(Collectors.joining(", ")).toLowerCase());
        }
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
