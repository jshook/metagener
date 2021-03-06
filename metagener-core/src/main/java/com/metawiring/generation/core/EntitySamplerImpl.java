package com.metawiring.generation.core;

import com.metawiring.configdefs.MutableFuncDef;
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
    private EntitySample lastEntitySample;
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
        this.lastEntitySample = es;
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

    public <T> T getLastFieldValue(String fieldName) {
        return lastEntitySample.getFieldValue(fieldName);
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
            MutableFuncDef funcDef = new MutableFuncDef();
            funcDef.setFuncName(entityDef.getName() + ":" + fieldDef.getFieldName());

            // If the field function def is empty, or does not specifically call sampleid(),
            // then implicitly call entityid() before applying field function chain.
            // entityid() is the result of the entity sampler function, as defined on the sampler definition

            String startFunc = null;
            if (fieldDef.getFieldFuncDef() !=null && fieldDef.getFieldFuncDef().getFuncCallDefs()!=null) {
                startFunc = fieldDef.getFieldFuncDef().getFuncCallDefs().get(0).getFuncName();
            }

            if (startFunc==null || !startFunc.equals("sampleid")) {
                funcDef.addFuncCallDefs(samplerDef.getSamplerFuncDef());
            }

            funcDef.addFuncCallDefs(fieldDef.getFieldFuncDef());
            logger.debug("funcChain: " + funcDef.toString());

            try {
                TypedFieldFunction<?> fieldSpecificFunction = FieldFunctionCompositor.composeFieldFunction(funcDef, this, fieldDef.getFieldType());
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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("EntitySampler{ ");
        sb.append("samplerDef=").append(samplerDef).append(";");
        return sb.toString();
    }
}
