package com.metawiring.clientapi;

/**
 * Represents a single immutable entity sample.
 */
public interface EntitySample {

    /**
     * Get the id of the named entity.
     * @return entity id
     */
    public long getEntityId();

    /**
     * Get the value of the named field of this entity instance.
     * @param fieldName field name
     * @param <T> generic return type (We'll see if this works well)
     * @return an object of type T, the field value
     */
    public <T> T getFieldValue(String fieldName);
}
