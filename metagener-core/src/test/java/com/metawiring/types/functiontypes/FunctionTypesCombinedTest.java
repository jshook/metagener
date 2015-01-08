package com.metawiring.types.functiontypes;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FunctionTypesCombinedTest {

    @Test
    public void testLongFieldFunction() {
        Plus plus = new Plus(3l);
        long result = plus.applyAsLong(4l);
        assertThat(result,is(7l));
    }

    @Test
    public void testLongComposeLong() {
        Plus plus3 = new Plus(3l);
        Mod mod2 = new Mod(2l);

        LongFieldFunction modComposePlus = mod2.compose(plus3);
        long mod2ComposePlus3 = modComposePlus.applyAsLong(17l);
        assertThat(mod2ComposePlus3,is(0l));

        LongFieldFunction plusComposeMod = plus3.compose(mod2);
        long plus3ComposeMod2 = plusComposeMod.applyAsLong(17l);
        assertThat(plus3ComposeMod2,is(4l));
    }

    @Test
    public void testLongAndThenLong() {
        Plus plus13 = new Plus(13l);
        Mod mod11 = new Mod(11l);

        LongFieldFunction modAndThenPlus = mod11.andThen(plus13);
        long mod11AndThenPlus13 = modAndThenPlus.applyAsLong(19l);
        assertThat(mod11AndThenPlus13,is(21l));

        LongFieldFunction plusAndThenMod = plus13.andThen(mod11);
        long plus13AndThenMod11 = plusAndThenMod.applyAsLong(19l);
        assertThat(plus13AndThenMod11,is(10l));

    }

    @Test
    public void testLongAndThenTypedFieldFunction() {
        Plus plus13 = new Plus(13l);
        ModString mods11 = new ModString(11l);

        TypedFieldFunction<String> tff = plus13.andThen(mods11);

        String plus13AndThenModString11 = tff.apply(19l);
        assertThat(plus13AndThenModString11,is("10"));
    }

    @Test
    public void testTypedFieldFunction() {
        ModString mods19 = new ModString(19l);
        String result = mods19.apply(27l);
        assertThat(result,is("8"));
    }

    @Test
    public void testGenericFieldFunction() {
        StringModString smods31 = new StringModString(31l);
        String result = smods31.apply("64");
        assertThat(result,is("2"));
    }

    @Test
    public void testGenericFieldFunctionComposeTypedFieldFunction() {
        StringModString smods7 = new StringModString(7l);
        ModString mods31 = new ModString(31l);
        GenericFieldFunction<Long, String> StringModStringComposeLongModString = smods7.compose(mods31);
        String result = StringModStringComposeLongModString.apply(73l);
        assertThat(result,is("4"));
    }

    @Test
    public void testTypedFieldFunctionAndThenGenericFieldFunction() {

    }

    @Test
    public void testGenericFieldFunctionComposeGenericFieldFunction() {

    }


    private static class Plus implements LongFieldFunction {
        private long plusAmount;
        public Plus(long plusAmount) { this.plusAmount = plusAmount; }
        @Override
        public long applyAsLong(long operand) {
            return operand + plusAmount;
        }
    }

    private static class Mod implements LongFieldFunction {
        private long modAmount;
        public Mod(long modAmount) { this.modAmount = modAmount; }
        @Override
        public long applyAsLong(long operand) {
            return operand % modAmount;
        }
    }

    private static class ModString implements TypedFieldFunction<String> {
        private long modAmount;
        public ModString(long modAmount) { this.modAmount = modAmount;}
        @Override
        public String apply(long value) {
            return String.valueOf(value % modAmount);
        }
    }

    private static class StringModString implements GenericFieldFunction<String,String> {
        private long modAmount;
        public StringModString(long modAmount) { this.modAmount = modAmount; }
        @Override
        public String apply(String s) {
            return String.valueOf(Long.valueOf(s) % modAmount);
        }
    }


}