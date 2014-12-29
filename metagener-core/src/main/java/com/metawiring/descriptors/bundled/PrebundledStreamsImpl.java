package com.metawiring.descriptors.bundled;

import com.metawiring.types.PrebundledStreams;
import com.metawiring.types.EntitySamplerService;

public class PrebundledStreamsImpl implements PrebundledStreams {

    @Override
    public EntitySamplerService getRetailStreams() {
        return new RetailEntitySamplerService();
    }
}
