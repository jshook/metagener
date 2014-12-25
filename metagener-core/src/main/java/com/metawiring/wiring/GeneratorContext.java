package com.metawiring.wiring;

import com.metawiring.clientapi.EntitySampleStream;
import com.metawiring.coreapi.EntityDescriptor;
import com.metawiring.coreapi.SampleStreamDescriptor;
import com.metawiring.entities.SimpleEntitySampleStream;

import java.util.HashMap;
import java.util.Map;

/**
 * You should only have one generator context per app
 */
public class GeneratorContext {

    private Map<String,EntityDescriptor> entityDescriptorMap = new HashMap<>();
    private Map<String,SampleStreamDescriptor> streamDescriptorMap = new HashMap<>();

    private Map<String,EntitySampleStream> sampleStreamMap = new HashMap<>();

    public EntitySampleStream getEntitySampleStream(String entitySampleStreamName) {
        EntitySampleStream ess = sampleStreamMap.get(entitySampleStreamName);
        if (ess==null) {
            synchronized (this) {
                ess = sampleStreamMap.get(entitySampleStreamName);
                if (ess==null) {
                    ess = createSampleStream(entitySampleStreamName);
                    sampleStreamMap.put(entitySampleStreamName,ess);
                }
            }
        }
        return ess;

    }

    private EntitySampleStream createSampleStream(String entitySampleStreamName) {
        SampleStreamDescriptor essd = streamDescriptorMap.get(entitySampleStreamName);
        if (essd==null) {
            throw new RuntimeException("attempt to access undefined stream [" + entitySampleStreamName + "]");
        }
        SimpleEntitySampleStream sampleStream = new SimpleEntitySampleStream(essd);
        sampleStream.resolveEntities(entityDescriptorMap);
        return sampleStream;
    }

}
