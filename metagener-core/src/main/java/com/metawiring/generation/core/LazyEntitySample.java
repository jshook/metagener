package com.metawiring.generation.core;

import com.metawiring.types.EntitySample;
import com.metawiring.types.EntitySampler;

import java.util.Map;

public class LazyEntitySample implements EntitySample {

    private final long sampleId;
    private final EntitySampler originEntitySampler;

    public LazyEntitySample(long sampleId, EntitySampler originEntitySampler) {
        this.sampleId = sampleId;
        this.originEntitySampler = originEntitySampler;
    }

    @Override
    public long getSampleId() {
        return sampleId;
    }

    @Override
    public <T> T getFieldValue(String fieldName) {
        T val = originEntitySampler.getFieldValue(fieldName, sampleId);
        return val;
    }

    @Override
    public Object[] getFieldValues() {
        return originEntitySampler.getFieldValues(sampleId);

    }

    @Override
    public Map<String, Object> getOrderedFieldMap() {
        return originEntitySampler.getFieldValueMap(sampleId);
    }
}
