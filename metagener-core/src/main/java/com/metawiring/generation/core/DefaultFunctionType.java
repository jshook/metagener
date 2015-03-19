package com.metawiring.generation.core;

import com.metawiring.generation.fieldgenboxes.*;
import com.metawiring.types.functiontypes.TypedFieldFunction;

public enum DefaultFunctionType {
    ASCII     (BoxedString.class),
    BIGINT    (BoxedBigInteger.class),
    BLOB      (BoxedByteBuffer.class),
    BOOLEAN   (BoxedBoolean.class),
    COUNTER   (BoxedLong.class),
    DECIMAL   (BoxedBigDecimal.class),
    DOUBLE    (BoxedDouble.class),
    FLOAT     (BoxedFloat.class),
    INET      (BoxedInetAddress.class),
    INT       (BoxedInteger.class),
    LIST      (BoxedList.class),
    MAP       (BoxedMap.class),
    SET       (BoxedSet.class),
    TEXT      (BoxedString.class),
    TIMESTAMP (BoxedDateTime.class),
    UUID      (BoxedUUID.class),
    TIMEUUID  (BoxedUUID.class),
    VARCHAR   (BoxedString.class),
    VARINT    (BoxedBigInteger.class);

    Class<? extends TypedFieldFunction> defaultTypedFieldFunctionClass;

    DefaultFunctionType(Class<? extends TypedFieldFunction> defaultTypedFieldFunctionClass) {
        this.defaultTypedFieldFunctionClass = defaultTypedFieldFunctionClass;
    }

    public Class<? extends TypedFieldFunction> getDefaultTypedFieldFunctionClass() {
        return defaultTypedFieldFunctionClass;
    }

    public static DefaultFunctionType typeOf(String name) {
        return valueOf(name.toUpperCase());
    }

}
