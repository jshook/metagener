package com.metawiring.fields;

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
    type_float(Float.class, "flot"),
    type_inet(InetAddress.class, "inet"),
    type_int(Integer.class, "int"),
    type_list(List.class, "list"),
    type_map(Map.class, "map"),
    type_set(Set.class, "set"),
    type_text(String.class, "text"),
    type_timestamp(Long.class, "timestamp"),
    type_uuid(UUID.class, "uuid"),
    type_timeuuid(UUID.class, "timeuuid"),
    type_varchar(String.class, "varchar"),
    type_varint(BigInteger.class, "varint");

    Class<?> clazz;
    String typename;

    FieldType(Class<?> clazz, String typeName) {
        this.clazz = clazz;
    }


}
