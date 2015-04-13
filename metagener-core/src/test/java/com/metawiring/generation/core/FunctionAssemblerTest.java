package com.metawiring.generation.core;

import com.metawiring.generation.fieldgenboxes.BoxedString;
import com.metawiring.generation.fieldgenericfuncs.Prefix;
import com.metawiring.generation.fieldgenericfuncs.Suffix;
import com.metawiring.generation.fieldgenfuncs.MinDouble;
import com.metawiring.generation.longfuncs.LongModulo;
import com.metawiring.types.functiontypes.LongUnaryFieldFunction;
import com.metawiring.types.functiontypes.TypedFieldFunction;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FunctionAssemblerTest {


    @Test
    public void testAndThenLongUnaryChain() throws Exception {

        FunctionAssembler assy = new FunctionAssembler();
        assy.andThen(new LongModulo(6));
        assy.andThen(new LongModulo(3));

        LongUnaryFieldFunction mod6then3 = assy.getlongFunction();

        long l = mod6then3.applyAsLong(23429l);
        assertThat(l,is(2l));

    }

    @Test
    public void testAndThenLongUnaryTypedUnaryChain() throws Exception {
        FunctionAssembler assy = new FunctionAssembler();
        assy.andThen(new LongModulo(6));
        assy.andThen(new LongModulo(3));
        assy.andThen(new BoxedString());

        TypedFieldFunction bs = assy.getTypedFunction();
        Object stringResult = bs.apply(23429l);
        Class<?> clazz = stringResult.getClass();
        assertThat(clazz.getSimpleName(),is("String"));
        assertThat(stringResult,is("2"));
    }

    @Test(expectedExceptions = {RuntimeException.class}, expectedExceptionsMessageRegExp = "Input type of class java.lang.String must have a compatible inner, or previous function.*")
    public void testAndThenLongUnaryWRONGTypedUnaryChain() throws Exception {
        FunctionAssembler assy = new FunctionAssembler();
        assy.andThen(new LongModulo(6));
        assy.andThen(new LongModulo(3));
        assy.andThen(new Prefix("prefix-"));
    }

    @Test
    public void testAndThenLongUnaryTypedUnaryGenericChain() throws Exception {
        FunctionAssembler assy = new FunctionAssembler();
        assy.andThen(new LongModulo(6));
        assy.andThen(new LongModulo(3));
        assy.andThen(new BoxedString());

        TypedFieldFunction bs = assy.getTypedFunction();
        Object stringResult = bs.apply(23429l);
        Class<?> clazz = stringResult.getClass();
        assertThat(clazz.getSimpleName(),is("String"));
        assertThat(stringResult, is("2"));

        assy.andThen(new Prefix("pre-"));
        assy.andThen(new Suffix("-post"));

        TypedFieldFunction stringy = assy.getTypedFunction();
        Object pre2post = stringy.apply(23429l);
        assertThat(pre2post,is("pre-2-post"));

    }

    @Test(expectedExceptions = {RuntimeException.class}, expectedExceptionsMessageRegExp = "Unable to wrap long=><R>.*")
    public void testAndThenLongUnaryTypedUnaryWRONGGenericChain() throws Exception {
        FunctionAssembler assy = new FunctionAssembler();
        assy.andThen(new LongModulo(6));
        assy.andThen(new LongModulo(3));
        assy.andThen(new BoxedString());

        TypedFieldFunction bs = assy.getTypedFunction();
        Object stringResult = bs.apply(23429l);
        Class<?> clazz = stringResult.getClass();
        assertThat(clazz.getSimpleName(),is("String"));
        assertThat(stringResult, is("2"));

        assy.andThen(new MinDouble(1.0d));

        TypedFieldFunction stringy = assy.getTypedFunction();
        Object pre2post = stringy.apply(23429l);
        assertThat(pre2post,is("pre-2-post"));


    }

}