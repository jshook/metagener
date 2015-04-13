package com.metawiring.generation.core;

import com.metawiring.types.functiontypes.*;
import com.metawiring.types.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * @param funcDef   String specifier of function names and arguments, in the format "func1;func2:arg1,..."
     * @param es        The entity sampler which this composed function is associated to
     * @param fieldType
     * @return the composed function
     */
    @SuppressWarnings({"ConstantConditions", "unchecked"})
    public static TypedFieldFunction<?> composeFieldFunction(FuncDef funcDef, EntitySampler es, FieldType requiredResultType) {

        FunctionAssembler assy = new FunctionAssembler();

//        LongUnaryFieldFunction composedLongFunc = null;
//        TypedFieldFunction<?> composedTypedFunc = null;

        // A field's function is a composite of the entity id mapping part (specified with the sampler's "samplerFunction")
        // and the field value mapping functions.
        // If there is no samplerFunction

        if (funcDef.getFuncCallDefs().size() > 0) {

            for (FuncCallDef funcCallDef : funcDef.getFuncCallDefs()) {

                if (funcCallDef.getFuncName().equals("sampleid")) {
                    continue;
                }
                if (funcCallDef.getFuncName().isEmpty()) {
                    logger.debug("Empty function name for funcDef:" + funcDef + ", in entity sampler:" + es);
                    continue;
                }

                Object funcObject;
                try {
                    funcObject = FieldFunctionResolver.resolveAndCreateFunctionObject(funcCallDef);
                    if (funcObject instanceof EntityDefAware) {
                        ((EntityDefAware) funcObject).applyEntityDef(es.getEntityDef());
                    }

                    if (funcObject instanceof SamplerDefAware) {
                        ((SamplerDefAware) funcObject).applySamplerDef(es.getSamplerDef());
                    }
                } catch (Exception e) {
                    logger.error("error instantiating function", e);
                    throw new RuntimeException(e);
                }

                assy.andThen(funcObject);
            }

        }

        if (assy.getTypedFunction() == null) {
            TypedFieldFunction defaultFunc;

            try {
                defaultFunc = DefaultFunctionType
                        .typeOf(requiredResultType.name())
                        .getDefaultTypedFieldFunctionClass().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            assy.andThen(defaultFunc);
        }

        return assy.getTypedFunction();
    }
}
