package com.metawiring.generation;

import com.metawiring.types.EntityDef;
import com.metawiring.types.EntitySample;
import com.metawiring.types.EntitySampler;
import com.metawiring.types.FieldDef;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LazyEntitySample implements EntitySample {

    private final long entityId;
    private final long hashedEntityId;
    private final EntitySampler originEntitySampler;

    public LazyEntitySample(long entityId, long hashedEntityId, EntitySampler originEntitySampler) {
        this.entityId = entityId;
        this.hashedEntityId = hashedEntityId;
        this.originEntitySampler = originEntitySampler;
    }

    @Override
    public long getEntityId() {
        return entityId;
    }

    @Override
    public <T> T getFieldValue(String fieldName) {
        T val = originEntitySampler.getFieldValue(fieldName, entityId, hashedEntityId);
        return val;
    }

    @Override
    public Object[] getFieldValues() {
        return originEntitySampler.getFieldValues(entityId, hashedEntityId);

    }

    @Override
    public Map<String, Object> getPrettyFieldValues() {
        return originEntitySampler.getFieldValueMap(entityId, hashedEntityId);
    }
}
