package com.metawiring.coreapi;

import java.util.List;

public interface SampleStreamDescriptor {

    public List<EntityDescriptor> getEntityDescriptors();
    public List<SamplingDescriptor> getSamplingDescriptors();
}
