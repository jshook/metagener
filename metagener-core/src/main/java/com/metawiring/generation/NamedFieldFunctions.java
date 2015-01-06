package com.metawiring.generation;

import com.metawiring.generation.valuemapfunctions.*;

public enum NamedFieldFunctions {
    prefix(Prefix.class,"prefix:1-"),
    suffix(Suffix.class, "suffix: street"),
    modentity(EntityModulo.class,"modentity"),
    mod(Modulo.class,"mod:50"),
    identity(Identity.class,"identity"), // primarily for testing
    file(FileLineExtract.class,"file:streetnames"),
    numbername(NamedNumberString.class,"numbername"),
    intoverflow(IntOverflow.class,"intoverflow"),
    dist(PopulationSampler.class,"dist:uniform");


    private final Class<? extends FieldFunction> implClass;

    private final String exampleString;

    NamedFieldFunctions(Class<? extends FieldFunction> implClass, String exampleString) {
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
