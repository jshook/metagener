package com.metawiring.types;

import org.joda.time.DateTime;

import java.math.BigInteger;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public enum FieldType {

    type_ascii(String.class, "ascii"),
    type_bigint(BigInteger.class, "bigint"),
    type_blob(ByteBuffer.class, "blob"),
    type_boolean(Boolean.class, "boolean"),
    type_counter(Long.class, "counter"),
    type_decimal(Float.class, "decimal"),
    type_double(Double.class, "double"),
    type_float(Float.class, "float"),
    type_inet(InetAddress.class, "inet"),
    type_int(Integer.class, "int"),
    type_list(List.class, "list"),
    type_map(Map.class, "map"),
    type_set(Set.class, "set"),
    type_text(String.class, "text"),
    type_timestamp(DateTime.class, "timestamp"),
    type_uuid(UUID.class, "uuid"),
    type_timeuuid(UUID.class, "timeuuid"),
    type_varchar(String.class, "varchar"),
    type_varint(BigInteger.class, "varint");

    Class<?> clazz;
    String typeName;

    FieldType(
            Class<?> clazz,
            String typeName) {
        this.clazz = clazz;
        this.typeName = typeName;
    }

    public Class<?> getFieldClass() {
        return clazz;
    }

    public String getTypeName() {
        return typeName;
    }

    public static FieldType typeOf(String typeName) {
        for (FieldType fieldType : values()) {
            if (fieldType.typeName.equalsIgnoreCase(typeName)) {
                return fieldType;
            }
        }
        throw new RuntimeException("No matching FieldType for type name [" + typeName + "]");
    }

    public static FieldType typeOf(Class<?> targetType) {
        for (FieldType fieldType : values()) {
            if (fieldType.getFieldClass().equals(targetType)) {
                return fieldType;
            }
        }
        throw new RuntimeException("No matching FieldType for class [ " + targetType + "]");
    }
}
