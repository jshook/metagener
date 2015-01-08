package com.metawiring.generation.core;

import com.metawiring.generation.entityid.*;
import com.metawiring.generation.fieldgenboxes.BoxedString;
import com.metawiring.generation.fieldgenfuncs.*;
import com.metawiring.generation.fieldgenericfuncs.Prefix;
import com.metawiring.generation.fieldgenericfuncs.Suffix;
import com.metawiring.types.functiontypes.FieldFunction;

public enum FieldFunctionName {

    identity(Identity.class,"identity"), // primarily for testing
    murmur3(Murmur3Hash.class,"murmur3"),
    modentity(EntityModulo.class,"modentity"),
    mod(Modulo.class,"mod:50"),
    filesample(FileLineSampler.class,"filesample:streetnames"),
    filecycle(FileLineCycler.class,"filecycle:streetnames"),
    numbername(NamedNumberString.class,"numbername"),
    intmod(IntModulo.class,"intmod"),
    dist(PopulationSampler.class,"dist:uniform"),
    prefix(Prefix.class,"prefix:1-"),
    suffix(Suffix.class, "suffix: street"),
    tostring(BoxedString.class,"tostring");


    private final Class<? extends FieldFunction> implClass;

    private final String exampleString;

    FieldFunctionName(Class<? extends FieldFunction> implClass, String exampleString) {
        this.implClass = implClass;
        this.exampleString = exampleString;
    }

    public Class<? extends FieldFunction> getImplClass() {
        return implClass;
    }

    public String getExampleString() {
        return exampleString;
    }
}
