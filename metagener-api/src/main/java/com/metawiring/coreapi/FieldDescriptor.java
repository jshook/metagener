
package com.metawiring.coreapi;

public interface FieldDescriptor {
    public String getFieldName();
    public String getSoftType();
    public Class<?> getValueClass();

}
