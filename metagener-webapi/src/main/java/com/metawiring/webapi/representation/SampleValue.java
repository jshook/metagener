package com.metawiring.webapi.representation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.metawiring.types.EntitySample;

import java.util.Map;

public class SampleValue {

    private long sampleId;
    private Map<String,Object> fieldValues;

    public SampleValue(long sampleId, EntitySample entitySample) {
        this.sampleId = sampleId;
        this.fieldValues = entitySample.getOrderedFieldMap();
    }

    @JsonProperty
    public long getSampleId() {
        return sampleId;
    }

    public void setSampleId(long sampleId) {
        this.sampleId = sampleId;
    }

    @JsonProperty
    public Map<String, Object> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(Map<String, Object> fieldValues) {
        this.fieldValues = fieldValues;
    }
}
