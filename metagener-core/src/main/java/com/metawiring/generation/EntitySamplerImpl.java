package com.metawiring.generation;

import com.metawiring.generation.entityhashfunctions.Murmur3AHashFunction;
import com.metawiring.types.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * This is not designed to be thread-safe.
 */
public class EntitySamplerImpl implements EntitySampler {

    private final SamplerDef samplerDef;
    private final EntityDef entityDef;

    // hashes the entity id, to provide some pseudo-random, yet idempotent field values
    private EntityHashFunction entityHashFunction = new Murmur3AHashFunction();

    // The source of long values which identify each entity sample.
    private LongStream identifierStream;
    private LongStream hashedIdentifierStream;

    private EntityGeneratorFunction<EntitySample> entityGeneratorFunction;

    // cached field functions, in array form
    private FieldFunction[] fieldFunctions;

    // cached field functions, in map form, same as above, but accessible by name
    private Map<String, FieldFunction<?>> fieldFunctionMap = new HashMap<>();

    // memoized elements, recomputed when the source stream is set
    private Stream<EntitySample> esStream;
    private Iterator<EntitySample> esIterator;
//    private List<Iterator<?>> fieldIterators;

    public EntitySamplerImpl(SamplerDef samplerDef, EntityDef entityDef) {
        this.samplerDef = samplerDef;
        this.entityDef = entityDef;
    }

    @Override
    public Stream<EntitySample> getEntityStream() {
        return esStream;
    }

    @Override
    public EntitySample getEntity(long entityId) {
        EntitySample es = entityGeneratorFunction.apply(entityId);
        return es;
    }

    @Override
    public EntitySample getNextEntity() {
        EntitySample es = esIterator.next();
        return es;
    }

    @Override
    public SamplerDef getSamplerDef() {
        return samplerDef;
    }

    @Override
    public <T> T getFieldValue(String fieldName, long entityId, long hashedEntityId) {
        FieldFunction<?> fieldFunction = fieldFunctionMap.get(fieldName);
        return null;
    }

    @Override
    public Object[] getFieldValues(long entityId, long hashedEntityId) {
        Object[] values = new Object[fieldFunctions.length];

        // TODO: Fix this. Field generator functions need to be able to specify
        // TODO: when they are monotonic and when they are pseudo-random
        // TODO: as of now, they are all pseudo-random
        for (int idx=0;idx<fieldFunctions.length;idx++) {
            Object val = fieldFunctions[idx].apply(hashedEntityId);
            values[idx]=val;
        }

        return values;
    }

    @Override
    public Map<String, Object> getFieldValueMap(long entityId, long hashedEntityId) {
        LinkedHashMap<String,Object> valmap = new LinkedHashMap<>(fieldFunctions.length);

        for (int idx=0;idx<fieldFunctions.length;idx++) {
            Object val = fieldFunctions[idx].apply(hashedEntityId);
            valmap.put(entityDef.getFieldDefs().get(idx).getFieldName(),val);
        }
        return valmap;
    }

    public void resolvePipeline() {

        identifierStream = LongStream.range(0l, Long.MAX_VALUE);

        fieldFunctions = new FieldFunction<?>[entityDef.getFieldDefs().size()];

        // TODO: parameterize field generator functions by stream type (monotone|PRNG)
        // TODO: memoize PRNG data for subsequent field generator cycles
        int defOffset = 0;
        for (FieldDef fieldDef : entityDef.getFieldDefs()) {
            try {
                fieldFunctions[defOffset] = fieldDef.getFieldType().getDefaultFieldFunction().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            defOffset++;
        }

        entityGeneratorFunction = new EntityGeneratorFunction<EntitySample>() {
            @Override
            public EntitySample apply(long value) {
                long hashed = entityHashFunction.applyAsLong(value);
                return new LazyEntitySample(value, hashed, EntitySamplerImpl.this);
            }
        };

        //this.esIterator =
        esIterator = identifierStream.mapToObj(entityGeneratorFunction).iterator();

    }
}
