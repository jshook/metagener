package com.metawiring.entities;

import com.metawiring.clientapi.EntitySample;
import com.metawiring.clientapi.EntitySampleStream;
import com.metawiring.coreapi.EntityDescriptor;
import com.metawiring.coreapi.SampleStreamDescriptor;
import com.metawiring.fields.FieldGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class SimpleEntitySampleStream implements EntitySampleStream {

    private final SampleStreamDescriptor sampleStreamDescriptor;

    private AtomicLong lastEntityId = new AtomicLong(0l);

    private Map<String,FieldGenerator<?>> fieldGenerators = new HashMap<>();

    public SimpleEntitySampleStream(SampleStreamDescriptor essd) {
        this.sampleStreamDescriptor = essd;
    }

    @Override
    public EntitySample getEntity(long entityId) {
        return new EntitySample(entityId,EntityDescriptor);
    }

    @Override
    public EntitySample getNextEntity() {
        long nextSample = lastEntityId.incrementAndGet();
        return new EntitySample(nextSample,EntityDescriptor);
    }

    @Override
    public SampleStreamDescriptor getSampleStreamDescriptor() {
        return sampleStreamDescriptor;
    }

    public void resolveEntities(Map<String, EntityDescriptor> entityDescriptorMap) {
    }
}
