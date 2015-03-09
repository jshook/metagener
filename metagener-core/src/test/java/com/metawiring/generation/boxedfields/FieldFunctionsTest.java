package com.metawiring.generation.boxedfields;

import com.metawiring.configdefs.MutableEntityDef;
import com.metawiring.configdefs.MutableSamplerDef;
import com.metawiring.generation.longfuncs.Identity;
import com.metawiring.generation.longfuncs.IntModulo;
import com.metawiring.generation.longfuncs.LongLongUnaryDiagnostic;
import com.metawiring.generation.longfuncs.Modulo;
import com.metawiring.generation.fieldgenfuncs.*;
import com.metawiring.generation.fieldgenericfuncs.Prefix;
import com.metawiring.generation.fieldgenericfuncs.StringStringDiagnostic;
import com.metawiring.generation.fieldgenericfuncs.Suffix;
import com.metawiring.types.functiontypes.EntityDefAware;
import com.metawiring.types.functiontypes.SamplerDefAware;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FieldFunctionsTest {

    @Test
    public void testPrefix() {
        Prefix prefix = new Prefix("-prefix-");
        String actual= prefix.apply("unit");
        assertThat(actual,is("-prefix-unit"));
    }

    @Test
    public void testSuffix() {
        Suffix suffix = new Suffix("-suffix-");
        String result = suffix.apply("unit");
        assertThat(result,is("unit-suffix-"));
    }

    @Test
    public void testIdentity() {
        Identity id = new Identity();
        Long result = id.apply(2345l);
        assertThat(result,is(2345l));
    }

    @Test
    public void testEntityModulo() {
        EntityModulo em = new EntityModulo();
        applyEntityDef("entity name", 53, em);
        Long result1 = em.applyAsLong(57l);
        assertThat(result1, is(4l));
    }

    @Test
    public void testDateTimeField() {
        DateTimeField dtf = new DateTimeField("YYYY-MM-dd-HH-mm-ss.mmm");
        long instantAt = new DateTime(2015,11,5,2,6,7, DateTimeZone.UTC).getMillis();
        String result = dtf.apply(instantAt);
        // Haven't clearly identified the millisecond offset yet
        assertThat(result,is("2015-11-05-02-06-07.006"));
    }

    @Test
    public void testLongLongDiagnostic() {
        LongLongUnaryDiagnostic lld = new LongLongUnaryDiagnostic("3");
        applyEntityDef("lldiag",234l,lld);
        applySamplerDef("samplerName","entityName","binomial",lld);
        Long result102=lld.applyAsLong(102l);
        assertThat(result102,is(102l));
        Long result203=lld.applyAsLong(203l);
        assertThat(result203,is(203l));
        Long result304=lld.applyAsLong(304l);
        assertThat(result304,is(304l));
    }

    @Test
    public void testStringStringDiagnostic() {
        StringStringDiagnostic ssd = new StringStringDiagnostic("2");
        applyEntityDef("lldiag",234l,ssd);
        applySamplerDef("samplerName","entityName","binomial",ssd);
        String result1 = ssd.apply("aresult");
        String result2 = ssd.apply("aresult");
        String result3 = ssd.apply("aresult");
        assertThat(result3,is("aresult"));
    }

    @Test
    public void testNamedNumberString() {
        NamedNumberString nns = new NamedNumberString();
        String resultZero = nns.apply(0l);
        assertThat(resultZero,is("zero"));
        String result153 = nns.apply(153l);
        assertThat(result153,is("one hundred and fifty three"));
    }

    @Test
    public void testModulo() {
        Modulo modulo = new Modulo("3");
        Long modulo3by3 = modulo.applyAsLong(3l);
        assertThat(modulo3by3,is(0l));
        Long modulo3by5 = modulo.applyAsLong(5l);
        assertThat(modulo3by5,is(2l));
    }

    @Test
    public void testIntModulo() {
        IntModulo io = new IntModulo();
        Long ioCeiling=io.apply((long)Integer.MAX_VALUE);
        assertThat(ioCeiling,is(0l));
        Long overBy1 = io.apply((long)Integer.MAX_VALUE+1);
        assertThat(overBy1,is(1l));
        Long overBy1cycleAnd1 = io.apply((long)Integer.MAX_VALUE + (long)Integer.MAX_VALUE + 1l);
        assertThat(overBy1cycleAnd1,is(1l));
    }

    @Test
    public void testFileLineCycler() {
        FileLineSampler fle1 = new FileLineSampler("testlines.txt");
        String result = fle1.apply(12l);
        assertThat(result,is("0 zero"));
        String result2 = fle1.apply(Long.MAX_VALUE-12l);
        assertThat(result2,is("10 sixteen"));
    }

    private void applySamplerDef(String samplerName, String entityName, String dist, SamplerDefAware ff) {
        MutableSamplerDef sd = new MutableSamplerDef();
        sd.setSamplerName(samplerName);
        sd.setEntityName(entityName);
        sd.setSamplerFunc(dist);
        ff.applySamplerDef(sd.immutable());
    }

    private void applyEntityDef(String s, long i, EntityDefAware ff) {
        MutableEntityDef med = new MutableEntityDef();
        med.setName(s);
        med.setPopulationSize(i);
        ff.applyEntityDef(med.immutable());
    }


}