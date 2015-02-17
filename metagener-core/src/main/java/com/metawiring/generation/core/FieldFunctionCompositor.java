package com.metawiring.generation.core;

import com.metawiring.configdefs.FormatConstants;
import com.metawiring.configdefs.MutableFuncDef;
import com.metawiring.generation.fieldgenboxes.BoxedString;
import com.metawiring.types.functiontypes.*;
import com.metawiring.types.*;
import com.metawiring.types.functiontypes.LongFieldFunction;
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
    // This class is mostly a way to achieve the specific type of function pipeline needed
    // for the  long... -> type conversion -> decoration...  style of composition, without
    // completely sacrificing the ability to use lambdas with primitive types.
    // J8 really didn't solve this in a consistent way.

    /**
     * chain functions: one or more LongFieldFunctions, and a TypedFieldFunction, and optional GenericFieldFunctions
     *
     * @param funcDef String specifier of function names and arguments, in the format "func1;func2:arg1,..."
     * @param es      The entity sampler which this composed function is associated to
     * @return the composed function
     */
    @SuppressWarnings({"ConstantConditions", "unchecked"})
    public static TypedFieldFunction<?> composeFieldFunction(FuncDef funcDef, EntitySampler es) {
        LongFieldFunction longFuncs = null;
        TypedFieldFunction<?> typedFuncs = null;
        GenericFieldFunction<?, ?> genericFuncs = null;

        // A field's function is a composite of the entity id mapping part (specified with the sampler's "samplerFunction")
        // and the field value mapping functions.
        // If there is no samplerFunction

        if (funcDef.getFuncCallDefs().size() > 0) {

            for (FuncCallDef functionSpec : funcDef.getFuncCallDefs()) {

                if (functionSpec.getFuncName().isEmpty()) {
                    logger.debug("Empty function name for funcDef:" + funcDef + ", in entity sampler:" + es);
                    continue;
                }

                Object funcObject;
                try {
                    funcObject = FieldFunctionResolver.resolveFunctionObject(functionSpec);
                } catch (Exception e) {
                    logger.error("error instantiating function", e);
                    throw new RuntimeException(e);
                }


                if (funcObject instanceof EntityDefAware) {
                    ((EntityDefAware) funcObject).applyEntityDef(es.getEntityDef());
                }

                if (funcObject instanceof SamplerDefAware) {
                    ((SamplerDefAware) funcObject).applySamplerDef(es.getSamplerDef());
                }


                if (funcObject instanceof LongFieldFunction) {
                    LongFieldFunction ff = (LongFieldFunction) funcObject;
                    longFuncs = (longFuncs == null) ? ff : longFuncs.andThen(ff);

                } else if (funcObject instanceof TypedFieldFunction<?>) {
                    TypedFieldFunction tff = (TypedFieldFunction) funcObject;

                    if (longFuncs == null) {
                        logger.warn("typed function [" + tff + "] follows zero long functions. This is probably wrong.");
                    }

                    typedFuncs = (longFuncs == null) ? tff : tff.compose(longFuncs);

                } else if (funcObject instanceof GenericFieldFunction<?, ?>) {
                    GenericFieldFunction gff = (GenericFieldFunction) funcObject;
                    if (typedFuncs == null) {
                        throw new RuntimeException("You may only use a generic function after a typed function.");
                    }
                    typedFuncs = typedFuncs.andThen(gff);
                }
            }
            // TODO: Document why there is long, typed, and generic ordering and the pressures it creates
        }


        if (typedFuncs == null) {
            typedFuncs = (longFuncs == null) ? new BoxedString() : longFuncs.andThen(new BoxedString());
        }

        return typedFuncs;
    }
}
