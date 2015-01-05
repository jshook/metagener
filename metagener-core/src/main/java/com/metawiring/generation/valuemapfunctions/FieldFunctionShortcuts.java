package com.metawiring.generation.valuemapfunctions;

import com.metawiring.generation.FieldFunction;

public enum FieldFunctionShortcuts {
    prefix(Prefix.class,"prefix:1-"),
    suffix(Suffix.class, "suffix: street"),
    modentity(EntityModulo.class,"modentity"),
    mod(Modulo.class,"mod:50"),
    identity(Identity.class,"identity"), // primarily for testing
    extract(FileLineExtract.class,"extract:streetnames"),
    numbername(NamedNumberString.class,"numbername"),
    modoverflow(LongModuloInt.class,"modoverflow");


    private final Class<? extends FieldFunction> implClass;

    private final String exampleString;

    FieldFunctionShortcuts(Class<? extends FieldFunction> implClass, String exampleString) {
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
