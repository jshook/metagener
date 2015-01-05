package com.metawiring.generation;

import com.metawiring.generation.entityhashfunctions.Murmur3Hash;
import com.metawiring.types.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(EntitySampler.class);

    private final SamplerDef samplerDef;
    private final EntityDef entityDef;

    // hashes the entity id, to provide some pseudo-random, yet idempotent field values
    private EntityHashFunction entityHashFunction = new Murmur3Hash();

    // The source of long values which identify each entity sample.
    private LongStream identifierStream;
    private LongStream hashedIdentifierStream;

    private EntityGeneratorFunction<EntitySample> entityGeneratorFunction;

    // cached field functions, in array form
    private FieldFunction[] fieldFunctions;

    // cached field functions, in map form, same as above, but accessible by name
    private Map<String, FieldFunction> fieldFunctionMap = new HashMap<>();

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
    @SuppressWarnings("unchecked")
    public <T> T getFieldValue(String fieldName, long sampleId) {
        FieldFunction<Long,T> fieldFunction = fieldFunctionMap.get(fieldName);
        return null;
    }

    @Override
    public Object[] getFieldValues(long sampleId) {
        Object[] values = new Object[fieldFunctions.length];

        // TODO: Fix this. Field function functions need to be able to specify
        // TODO: when they are monotonic and when they are pseudo-random
        // TODO: as of now, they are all pseudo-random
        for (int idx=0;idx<fieldFunctions.length;idx++) {
            Object val = fieldFunctions[idx].apply(sampleId);
            values[idx]=val;
        }

        return values;
    }

    @Override
    public Map<String, Object> getFieldValueMap(long sampleId) {
        LinkedHashMap<String,Object> valmap = new LinkedHashMap<>(fieldFunctions.length);

        for (int idx=0;idx<fieldFunctions.length;idx++) {
            Object val = fieldFunctions[idx].apply(sampleId);
            valmap.put(entityDef.getFieldDefs().get(idx).getFieldName(),val);
        }
        return valmap;
    }

    public void resolvePipeline() {

        identifierStream = LongStream.range(0l, Long.MAX_VALUE);

        fieldFunctions = new FieldFunction[entityDef.getFieldDefs().size()];

        // TODO: parameterize field function functions by stream type (monotone|PRNG)
        // TODO: memoize PRNG data for subsequent field function cycles
        int defOffset = 0;
        for (FieldDef fieldDef : entityDef.getFieldDefs()) {
            try {
                FieldFunction fieldSpecificFunction = composeFieldFunction(fieldDef);
                fieldFunctions[defOffset] = fieldSpecificFunction;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            defOffset++;
        }

        entityGeneratorFunction = new EntityGeneratorFunction<EntitySample>() {
            @Override
            public EntitySample apply(long value) {
                long hashed = entityHashFunction.applyAsLong(value);
                return new LazyEntitySample(value, EntitySamplerImpl.this);
            }
        };

        //this.esIterator =
        esIterator = identifierStream.mapToObj(entityGeneratorFunction).iterator();

    }

    @SuppressWarnings("unchecked")

    // There isn't a nice way to work around type-erasure and late-binding
    // Nor is there a nice way to work around autoboxing and composed functions...

    private FieldFunction<Long,?> composeFieldFunction(FieldDef fieldDef) {
        FieldFunction f =  null;
        FieldFunction nextfunc=null;
        Class<?> yieldedType = Long.class;

        for (String func : fieldDef.getFunction().split(",")) {
            try {
                nextfunc = FunctionFinder.find(func).newInstance();
            } catch (Exception e) {
                logger.error("error instantiating function", e);
                throw new RuntimeException(e);
            }
            if (nextfunc instanceof EntityDefAware) {
                ((EntityDefAware) nextfunc).applyEntityDef(this.entityDef);
            }
            if (nextfunc instanceof SamplerDefAware) {
                ((SamplerDefAware) nextfunc).applySamplerDef(this.samplerDef);
            }

            nextfunc.verifyInputType(yieldedType);
            yieldedType = nextfunc.getOutputType();

            if (f == null) {
                f = nextfunc;
            } else {
                f = f.andThen(nextfunc);
            }
        }
        if (nextfunc==null) {
            throw new RuntimeException("Composed function was null");
        }
        nextfunc.verifyOutputType(fieldDef.getFieldType().getFieldClass());

        return f;
    }
}
