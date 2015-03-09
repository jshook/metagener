package com.metawiring.generation.core;

import com.metawiring.configdefs.MutableEntityDef;
import com.metawiring.configdefs.MutableFieldDef;
import com.metawiring.configdefs.MutableSamplerDef;
import com.metawiring.syntax.MetagenerDSL;
import com.metawiring.types.EntityDef;
import com.metawiring.types.EntitySampler;
import com.metawiring.types.FieldDef;
import com.metawiring.types.SamplerDef;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class FieldFunctionCompositorTest {

    @Test
    public void testResolvesTypeToString() throws Exception {
        MutableFieldDef fieldDef = new MutableFieldDef();
        MetagenerDSL.chainedFunctionFromSyntax("modulo(5)");

        fieldDef.setFieldName("fieldname").setFieldType("text").setFieldFunc("modulo(5)");
        FieldFunctionCompositor.composeFieldFunction();



    }

    private EntitySampler getEntitySampler() {
        EntitySampler mockEntitySampler = mock(EntitySampler.class);
    }

    private SamplerDef getSamplerDef() {
        return new MutableSamplerDef().setSamplerName("test").setEntityName("test");
    }
    private EntityDef getEntityDef() {
        return new MutableEntityDef().setName("test").setPopulationSize(100);
    }

    p
}