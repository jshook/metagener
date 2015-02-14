package com.metawiring.generation.core;

import com.metawiring.types.functiontypes.TypedFieldFunction;
import com.metawiring.types.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.LongFunction;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * This is not designed to be thread-safe.
 */
public class EntitySamplerImpl implements EntitySampler {

    // TODO: Audit code for usages of "entityId" where it should actually be "sampleId"

    private static final Logger logger = LoggerFactory.getLogger(EntitySampler.class);

    private final SamplerDef samplerDef;
    private final EntityDef entityDef;

    // The source of long values which identify each entity sample.
    private LongStream identifierStream;
    private LongStream hashedIdentifierStream;

    private EntityGeneratorFunction<EntitySample> entityGeneratorFunction;

    // cached field functions, in array form
    private LongFunction[] fieldFunctions;

    // cached field functions, in map form, same as above, but accessible by name
    private Map<String, TypedFieldFunction> fieldFunctionMap = new HashMap<>();

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
    public EntitySample getEntity(long sampleId) {
        EntitySample es = entityGeneratorFunction.apply(sampleId);
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
        LongFunction function= fieldFunctionMap.get(fieldName);
        return (T) function.apply(sampleId);
    }

    @Override
    public Object[] getFieldValues(long sampleId) {
        Object[] values = new Object[fieldFunctions.length];

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

    @Override
    public EntityDef getEntityDef() {
        return entityDef;
    }

    public void resolvePipeline() {

        identifierStream = LongStream.range(0l, Long.MAX_VALUE);

        fieldFunctions = new LongFunction[entityDef.getFieldDefs().size()];

        // TODO: parameterize field function functions by stream type (monotone|PRNG)
        // TODO: memoize PRNG data for subsequent field function cycles, if possible without veering too far from pure functions
        int defOffset = 0;

        for (FieldDef fieldDef : entityDef.getFieldDefs()) {
            String fieldFuncChain = fieldDef.getFieldFunc();
            String samplerFuncChan = samplerDef.getSamplerFunc();
            String funcChain = "";
            funcChain += (samplerFuncChan==null || samplerFuncChan.isEmpty()) ? "" : samplerFuncChan + ";";
            funcChain += (fieldFuncChain==null || fieldFuncChain.isEmpty()) ? "" : fieldFuncChain;
            logger.debug("funcChain: " + funcChain);

            try {
                TypedFieldFunction<?> fieldSpecificFunction = FieldFunctionCompositor.composeFieldFunction(funcChain, this);
                fieldFunctions[defOffset] = (LongFunction<?>) fieldSpecificFunction;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            defOffset++;
        }

        entityGeneratorFunction = new EntityGeneratorFunction<EntitySample>() {
            @Override
            public EntitySample apply(long value) {
                return new LazyEntitySample(value, EntitySamplerImpl.this);
            }
        };

        //this.esIterator =
        esIterator = identifierStream.mapToObj(entityGeneratorFunction).iterator();

    }

}
