package com.metawiring.fields;

public class TextFieldGenerator implements FieldGenerator<String> {

    @Override
    public String generate(long entityId) {
        return String.valueOf(entityId);
    }

}
