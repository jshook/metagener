package com.metawiring.generation.boxedfields;

import com.metawiring.types.FieldFunctionSignature;
import com.metawiring.types.functiontypes.FieldFunction;
import com.metawiring.generation.core.FieldFunctionName;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FieldFunctionsAnnotatedTest {

    @Test(enabled = false)
    public void testAllNamedFunctionsHaveAnnotations() {
        for (FieldFunctionName ffn : FieldFunctionName.values())
        {
            Class<? extends FieldFunction> implClass = ffn.getImplClass();
            FieldFunctionSignature[] declaredAnnotationsByType = implClass.getDeclaredAnnotationsByType(FieldFunctionSignature.class);
            assertThat(declaredAnnotationsByType.length,is(1));
        }
    }

}