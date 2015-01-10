package com.metawiring.types;

import java.util.Map;
import java.util.stream.LongStream;

/**
 * Represents a single immutable entity sample.
 */
public interface EntitySample {

    /**
     * Get the id of the named entity.
     * @return entity id
     */
    public long getSampleId();

    /**
     * Get the value of the named field of this entity instance.
     * @param fieldName field name
     * @param <T> generic return type (We'll see if this works well)
     * @return an object of type T, the field value
     */
    public <T> T getFieldValue(String fieldName);

    /**
     * Return an ordered map of field names and values, in the same order tha the fields were defined in.
     * @return a map of named values, in defined order
     */
    public Map<String,Object> getPrettyFieldValues();

    /**
     * Return just the generated values, in the same order that the fields were defined in.
     * @return an object array of field values, in defined order
     */
    public Object[] getFieldValues();
}
