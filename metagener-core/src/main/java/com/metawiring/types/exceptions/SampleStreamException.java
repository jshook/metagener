package com.metawiring.types.exceptions;

import com.metawiring.types.EntitySampler;

public class SampleStreamException extends RuntimeException {

    private EntitySampler sampleStream;

    public SampleStreamException(String message) {
        super(message);
    }

    public SampleStreamException(Throwable cause, EntitySampler sampleStream) {
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
