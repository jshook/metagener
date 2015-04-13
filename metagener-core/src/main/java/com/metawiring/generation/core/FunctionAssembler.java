package com.metawiring.generation.core;

import com.metawiring.annotations.Input;
import com.metawiring.annotations.Output;
import com.metawiring.generation.fieldgenboxes.BoxedLong;
import com.metawiring.types.functiontypes.GenericFieldFunction;
import com.metawiring.types.functiontypes.LongUnaryFieldFunction;
import com.metawiring.types.functiontypes.TypedFieldFunction;

/**
 * This is a wrapper type which deals with the ugliness which is J8+J8 Function Type Insanity + J8 Functional Types + user-friendly late binding.
 * The goal of this class is to hide such ugliness from the rest of the code. It will be responsible for holding a composed function, remembering the
 * input and output types, and allowing for type-safe composition with efficient usage of non-boxed types when possible.
 * <p>
 * Only specific types of compositions are allowed:
 * <ul>
 * <li>stage 1 - LongUnaryFieldFunction()</li>
 * <li>stage 2 - TypedFieldFunction(LongUnaryFieldFunction())</li>
 * <li>stage 3 - GenericFieldFunction(TypedFieldFunction(LongUnaryFieldFunction()))</li>
 * </ul>
 * <p>
 * These represent, respectively,
 * <ul>
 * <li>f(long)=>long</li>
 * <li>f(long)=>&lt;R&gt;</li>
 * <li>f(&lt;T&gt;)=>&lt;R&gt;</li>
 * </ul>
 * <p>The final functional form is not represented as a GenericFieldFunction. TypedFieldFunction (long=>&lt;R&gt;) is the final signature.
 * The compositions serve only to compose more additional long functions around the original long input, or to compose a long function with a
 * generic result around that composed function, or to composed a generic function with a generic result around that composed function.
 * </p>
 * <p>The functions may be defined progressively from stage 1 through stage 3. For each stage, a default function will be dropped in place before it as
 * needed in order to provide a composed function signature of f(long)=>&lt;R&gt;
 * </p>
 * <p>
 * In the future, a better way of arranging these function more directly is desired. If anybody has ideas on how to do this cleanly, I'm interesting in hearing about it.
 * Specifically, we need to:
 * <ul>
 * <li>Maintain runtime awareness of function types, including primitive or boxed types</li>
 * <li>Use primitive types where possible</li>
 * <li>Compose functions with lambdas</li>
 * <li>eventually, support bifunctions, etc.</li>
 * </ul>
 * </p>
 */

public class FunctionAssembler {

    LongUnaryFieldFunction longFunction;
    TypedFieldFunction composedFunction;
    Class<?> outputType;

    public FunctionAssembler() {
    }

    public FunctionAssembler andThen(Object functionObject) {

        // ugly rtti
        if (functionObject instanceof LongUnaryFieldFunction) {
            return andThen((LongUnaryFieldFunction) functionObject);
        }
        if (functionObject instanceof TypedFieldFunction<?>) {
            return andThen((TypedFieldFunction<?>) functionObject);
        }
        if (functionObject instanceof GenericFieldFunction<?,?>) {
            return andThen((GenericFieldFunction<?,?>) functionObject);
        }
        throw new RuntimeException("Function object was not of recognizable type.");
    }

    public FunctionAssembler andThen(LongUnaryFieldFunction outerLongFunc) {
        if (composedFunction != null) {
            throw new RuntimeException("Unable to compose a LongUnaryFieldFunction around a TypedFieldFunction:" +
                    "outer:" + outerLongFunc + ", inner:" + composedFunction);
        }

        if (longFunction == null) {         // IF the initial function
            longFunction = outerLongFunc;
        } else {
            longFunction = longFunction.andThen(outerLongFunc);
        }
        return this;
    }

    public FunctionAssembler andThen(TypedFieldFunction<?> outerTypedFunc) {
        if (composedFunction == null) {
            composedFunction = outerTypedFunc;
        }
        if (longFunction != null) {
            composedFunction = composedFunction.compose(longFunction);
        }
        outputType = outputTypeFor(outerTypedFunc);
        return this;
    }

    public FunctionAssembler andThen(GenericFieldFunction<?, ?> outerGenericFunc) {
        Class<?> inType = inputTypeFor(outerGenericFunc);
        if (composedFunction == null) {
            if (inType.isAssignableFrom(long.class)) {
                BoxedLong boxedLong = new BoxedLong();
                composedFunction = new BoxedLong().andThen((GenericFieldFunction<Long, ?>) outerGenericFunc);
            } else {
                throw new RuntimeException("Input type of " + inType + " must have a compatible inner, or previous function, since it is not assignable from long.");
            }
        } else {
            if (inType.isAssignableFrom(outputType)) {
                composedFunction = composedFunction.andThen(outerGenericFunc);
                outputType = outputTypeFor(outerGenericFunc);
            } else {
                throw new RuntimeException("Unable to wrap long=><R> function of result type " + outputType + " with a <T>=><R> function of type" + inType);
            }
        }
        return this;
    }
    public LongUnaryFieldFunction getlongFunction() {
        return longFunction;
    }

    public TypedFieldFunction<?> getTypedFunction() {
        if (composedFunction!=null) {
            return composedFunction;
        }
        return null;
    }

    public Class<?> getOutputType() {
        return outputType;
    };

    private static Class<?> inputTypeFor(Object function) {
        Input annotation = function.getClass().getAnnotation(Input.class);
        return annotation.value()[0];
    }

    private static Class<?> outputTypeFor(Object function) {
        Output annotation = function.getClass().getAnnotation(Output.class);
        return annotation.value()[0];
    }



}
