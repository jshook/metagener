package com.metawiring.clientapi;

import com.metawiring.coreapi.EntityDescriptor;
import com.metawiring.coreapi.SampleStreamDescriptor;

/**
 * A stream of entity samples, and ways of learning about
 * the entity structure.
 */
public interface EntitySampleStream {

    /**
     * Get the numbered entity. This is for random access of generated
     * entities, but you probably want to use {@link #getNextEntity} instead.
     * @param entityId a pseudo-random entity with a stable identity based on the id
     */
    public EntitySample getEntity(long entityId);

    /**
     * Get the next entity based on the sampling configuration
     * @return a pseudo-random entity with a stable identity
     */
    public EntitySample getNextEntity();

    /**
     * Return the configuration of the sample stream
     * @return the configuration of this stream
     */
    public SampleStreamDescriptor getSampleStreamDescriptor();




}
