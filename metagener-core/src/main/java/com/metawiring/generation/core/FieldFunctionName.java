package com.metawiring.generation.core;

import com.metawiring.generation.longfuncs.*;
import com.metawiring.generation.fieldgenboxes.BoxedString;
import com.metawiring.generation.fieldgenfuncs.*;
import com.metawiring.generation.fieldgenericfuncs.Prefix;
import com.metawiring.generation.fieldgenericfuncs.Suffix;

public enum FieldFunctionName {

    identity(Identity.class),  // primarily for testing
    murmur3(Murmur3Hash.class),
    modentity(EntityModulo.class),
    mod(Modulo.class),

    filesample(FileLineSampler.class),
    filecycle(FileLineCycler.class),
    numbername(NamedNumberString.class),
    intmod(IntModulo.class),
    dist(PopulationSampler.class),
    prefix(Prefix.class),
    suffix(Suffix.class),
    tostring(BoxedString.class),
    daterange(DateRange.class);


    private final Class<?> implClass;

    FieldFunctionName(Class<?> implClass) {
        this.implClass = implClass;
    }

    public Class<?> getImplClass() {
        return implClass;
    }

}
