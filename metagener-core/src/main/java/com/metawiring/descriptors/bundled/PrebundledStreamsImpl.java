package com.metawiring.descriptors.bundled;

import com.metawiring.clientapi.PrebundledStreams;
import com.metawiring.clientapi.SampleStreamProvider;

public class PrebundledStreamsImpl implements PrebundledStreams {

    @Override
    public SampleStreamProvider getRetailStreams() {
        return RetailSampleStreamProvider.get();
    }
}
