package com.metawiring.types;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public enum FieldType {

    ASCII     (String.class),
    BIGINT    (BigInteger.class),
    BLOB      (ByteBuffer.class),
    BOOLEAN   (Boolean.class),
    COUNTER   (Long.class),
    DECIMAL   (BigDecimal.class),
    DOUBLE    (Double.class),
    FLOAT     (Float.class),
    INET      (InetAddress.class),
    INT       (Integer.class),
    LIST      (List.class),
    MAP       (Map.class),
    SET       (Set.class),
    TEXT      (String.class),
    TIMESTAMP (DateTime.class),
    UUID      (UUID.class),
    TIMEUUID  (UUID.class),
    VARCHAR   (String.class),
    VARINT    (BigInteger.class);

    Class<?> clazz;

    FieldType(Class<?> clazz) {
        this.clazz = clazz;
    }

    public static FieldType typeOf(String name) {
        return valueOf(name.toUpperCase());
    }

    public Class<?> getBackingClass() {
        return clazz;
    }
}
