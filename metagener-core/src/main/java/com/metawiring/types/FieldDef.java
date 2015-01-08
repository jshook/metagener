
package com.metawiring.types;

import com.metawiring.generation.core.FieldType;

public interface FieldDef {
    public String getFieldName();
    public FieldType getFieldType();
    public String getFunction();
}
