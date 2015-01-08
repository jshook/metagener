package com.metawiring.generation.core;

import com.metawiring.configdefs.FormatConstants;
import com.metawiring.types.functiontypes.*;
import com.metawiring.types.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Parses function specs, composes functions.
 */
public class FieldFunctionCompositor {

    private static final Logger logger = LoggerFactory.getLogger(FieldFunctionCompositor.class);

    // There isn't a nice way to work around type-erasure and late-binding
    // Nor is there a nice way to work around autoboxing and composed functions...

    /**
     * chain functions: one or more LongFieldFunctions, and a TypedFieldFunction, and optional GenericFieldFunctions
     * @param functionChain String specifier of function names and arguments, in the format "func1;func2:arg1,..."
     * @param es The entity sampler which this composed function is associated to
     * @return the composed function
     */
    public static FieldFunction composeFieldFunction(String functionChain, EntitySampler es) {
        FieldFunction f =  null;
        FieldFunction nextfunc=null;
        Class<?> yieldedType = Long.class;

        // A field's function is a composite of the entity id mapping part (specified with the sampler's "distribution")
        // and the field value mapping functions.
        // If there is no distribution
        List<String> functionSpecs = new ArrayList<>();
        Collections.addAll(functionSpecs,
                functionChain.split(FormatConstants.FUNC_DELIM)
        );

        for (String functionSpec : functionSpecs) {

            if (functionSpec.isEmpty()) {
                logger.debug("Empty function spec, for " + es);
                continue;
            }

            try {
                nextfunc = FieldFunctionResolver.resolveFieldFunction(functionSpec);
            } catch (Exception e) {
                logger.error("error instantiating function", e);
                throw new RuntimeException(e);
            }

            if (nextfunc instanceof EntityDefAware) {
                ((EntityDefAware) nextfunc).applyEntityDef(es.getEntityDef());
            }

            if (nextfunc instanceof SamplerDefAware) {
                ((SamplerDefAware) nextfunc).applySamplerDef(es.getSamplerDef());
            }

            if (f == null) {
                f = nextfunc;
                continue;
            }

            // Double dispatch might be better for this
            if (nextfunc instanceof LongFieldFunction && f instanceof LongFieldFunction) {
                f = ((LongFieldFunction) f).andThen((LongFieldFunction)nextfunc);
            } else if (nextfunc instanceof TypedFieldFunction && f instanceof LongFieldFunction) {
                f = ((LongFieldFunction) f).andThen((TypedFieldFunction) nextfunc);
            }
        }

        if (nextfunc==null) {
            throw new RuntimeException("Composed function was null");
        }

        return f;
    }
}
