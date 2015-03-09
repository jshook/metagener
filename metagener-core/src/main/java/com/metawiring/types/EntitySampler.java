package com.metawiring.types;

import java.util.Map;
import java.util.stream.Stream;

/**
 * A stream of entity samples, and ways of learning about
 * the definitions that created it.
 */
public interface EntitySampler {

    /**
     * Get a stream of entity samples. This returns the underlying stream
     * which is used to provide the other services, but which may be useful
     * for consumers that are Stream<?> oriented.
     * @return an &lt;EntitySample&gt; Stream
     */
    public Stream<EntitySample> getEntityStream();

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
    public SamplerDef getSamplerDef();

    /**
     * Return the generated field value of the named field, for the given entityId
     * @param fieldName The field name to sample
     * @param sampleId The entity id - to be used for monotonically increasing value generation
     * @param <T> The value type, according to the field type
     * @return An idempotent value for the (EntitySampler,fieldName,entityId) tuple
     */
    public <T> T getFieldValue(String fieldName, long sampleId);

    /**
     * Generate a list of ordered field values. This is the most efficient way to get all of the
     * field values for a specific entity id.
     * @param sampleId The entity Id
     * @return an array of field values, in the order that the fields were defined in
     */
    public Object[] getFieldValues(long sampleId);

    /**
     * Generate a map of named field values. This is generally wasteful, but easy to read
     * for diagnostics if you aren't optimizing for speed.
     * @param sampleId The entity id
     * @return A map of named field values, in the order that the fields were defined in.
     */
    public Map<String,Object> getFieldValueMap(long sampleId);

    public EntityDef getEntityDef();
}
