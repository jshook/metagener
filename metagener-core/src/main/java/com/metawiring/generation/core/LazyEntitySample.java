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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LazyEntitySample { ");
        sb.append("sampleId=").append(sampleId).append("; ");
        sb.append("originEntitySampler=").append(originEntitySampler).append("; ");
        sb.append("}");
        Map<String, Object> orderedFieldMap = getOrderedFieldMap();
        for (String fieldName : orderedFieldMap.keySet()) {
            sb.append("\n ").append(fieldName).append("=");
            sb.append(orderedFieldMap.get(fieldName)).append(";");
        }

        return sb.toString();
    }
}
