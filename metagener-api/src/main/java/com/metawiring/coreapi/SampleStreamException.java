package com.metawiring.coreapi;

import com.metawiring.clientapi.EntitySampleStream;

public class SampleStreamException extends RuntimeException {

    private EntitySampleStream sampleStream;

    public SampleStreamException(String message) {
        super(message);
    }

    public SampleStreamException(Throwable cause, EntitySampleStream sampleStream) {
        super(cause);
        this.sampleStream = sampleStream;
    }

    @Override
    public String getMessage() {
        return "There was an error while accessing sample stream [" +
                (sampleStream ==null ? "null" : sampleStream) + "]" +
                (getCause() != null ? ": " + getCause().getMessage() : "");
    }

}
