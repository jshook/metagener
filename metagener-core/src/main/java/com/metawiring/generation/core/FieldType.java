package com.metawiring.generation.core;

import com.metawiring.generation.fieldgenboxes.*;
import com.metawiring.types.functiontypes.TypedFieldFunction;
import org.joda.time.DateTime;

import java.math.BigInteger;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public enum FieldType {

    type_ascii(String.class, "ascii", BoxedString.class),
    type_bigint(BigInteger.class, "bigint", BoxedBigInt.class),
    type_blob(ByteBuffer.class, "blob", BoxedByteBuffer.class),
    type_boolean(Boolean.class, "boolean", BoxedBoolean.class),
    type_counter(Long.class, "counter", BoxedLong.class),
    type_decimal(Float.class, "decimal", BoxedFloat.class),
    type_double(Double.class, "double", BoxedDouble.class),
    type_float(Float.class, "float", BoxedFloat.class),
    type_inet(InetAddress.class, "inet", BoxedInetAddress.class),
    type_int(Integer.class, "int", BoxedInt.class),
    type_list(List.class, "list", BoxedList.class),
    type_map(Map.class, "map", BoxedMap.class),
    type_set(Set.class, "set", BoxedSet.class),
    type_text(String.class, "text", BoxedString.class),
    type_timestamp(DateTime.class, "timestamp", BoxedDateTime.class),
    type_uuid(UUID.class, "uuid", BoxedUUID.class),
    type_timeuuid(UUID.class, "timeuuid", BoxedUUID.class),
    type_varchar(String.class, "varchar", BoxedString.class),
    type_varint(BigInteger.class, "varint", BoxedBigInt.class);

    Class<?> clazz;
    String typeName;
    Class<? extends TypedFieldFunction> defaultFieldFunction;

    FieldType(
            Class<?> clazz,
            String typeName,
            Class<? extends TypedFieldFunction> defaultFieldFunctionClass) {
        this.clazz = clazz;
        this.typeName = typeName;
        this.defaultFieldFunction = defaultFieldFunctionClass;
    }

    public Class<?> getFieldClass() {
        return clazz;
    }

    public Class<? extends TypedFieldFunction> getDefaultFieldFunction() {
        return defaultFieldFunction;
    }

    public String getTypeName() {
        return typeName;
    }

    public static FieldType typeOf(String typeName) {
        for (FieldType fieldType : FieldType.values()) {
            if (fieldType.typeName.equalsIgnoreCase(typeName)) {
                return fieldType;
            }
        }
        throw new RuntimeException("No matching FieldType for type name [" + typeName + "]");
    }

    public static FieldType typeOf(Class<?> targetType) {
        for (FieldType fieldType : FieldType.values()) {
            if (fieldType.getFieldClass().equals(targetType)) {
                return fieldType;
            }
        }
        throw new RuntimeException("No matching FieldType for class [ " + targetType + "]");
    }
}
