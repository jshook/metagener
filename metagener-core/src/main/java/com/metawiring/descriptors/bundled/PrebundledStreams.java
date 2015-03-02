package com.metawiring.descriptors.bundled;

import com.metawiring.types.EntitySamplerService;

public class PrebundledStreams {

    /**
     * provides sample data for retail streams.
     * @return a retail EntitySamplerService
     */
    public static EntitySamplerService getRetailStreams() {
        return new RetailEntitySamplerService();
    }
}
