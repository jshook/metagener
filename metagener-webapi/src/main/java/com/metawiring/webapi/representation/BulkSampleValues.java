package com.metawiring.webapi.representation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class BulkSampleValues {

    private long minSampleId;

    public BulkSampleValues(long minId, long maxId, List<SampleValue> sampleValues) {
        this.minSampleId = minId;
        this.maxSampleId=maxId;
        this.sampleValues = sampleValues;
    }

    @JsonProperty
    public long getMinSampleId() {
        return minSampleId;
    }

    public void setMinSampleId(long minSampleId) {
        this.minSampleId = minSampleId;
    }

    @JsonProperty
    public long getMaxSampleId() {
        return maxSampleId;
    }

    public void setMaxSampleId(long maxSampleId) {
        this.maxSampleId = maxSampleId;
    }

    @JsonProperty
    public List<SampleValue> getSampleValues() {
        return sampleValues;
    }

    public void setSampleValues(List<SampleValue> sampleValues) {
        this.sampleValues = sampleValues;
    }

    private long maxSampleId;
    private List<SampleValue> sampleValues = new ArrayList<>();


}
