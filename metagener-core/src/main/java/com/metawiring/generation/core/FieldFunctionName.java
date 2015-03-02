package com.metawiring.generation.core;

import com.metawiring.generation.fieldgenericfuncs.LeftString;
import com.metawiring.generation.fieldgenericfuncs.RightString;
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
    modulopop(ModuloPop.class),

    numbname(NumbNamer.class),
    filesample(FileLineSampler.class),
    filecycle(FileLineCycler.class),
    numbername(NamedNumberString.class),
    intmod(IntModulo.class),
    multiply(Multiply.class),
    dist(PopulationSampler.class),
    prefix(Prefix.class),
    suffix(Suffix.class),
    tostring(BoxedString.class),
    dateshift(DateShift.class),
    daterange(DateRange.class),
    rangedrandom(RangedRandom.class),
    scaleddouble(ToScaledDouble.class),

    leftstring(LeftString.class),
    rightstring(RightString.class);

    private final Class<?> implClass;

    FieldFunctionName(Class<?> implClass) {
        this.implClass = implClass;
    }

    public Class<?> getImplClass() {
        return implClass;
    }

}
