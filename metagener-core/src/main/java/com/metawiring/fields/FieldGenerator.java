package com.metawiring.fields;

/**
 * A field generator knows how to render a value.
 * @param <T>
 */
public interface FieldGenerator<T> {
    /**
     * By default, a field is generated according to the entity id it is associated with.
     * @param entityID the unique id of an entity
     * @return a value of type T
     */
    public T generate(long entityID);
}
