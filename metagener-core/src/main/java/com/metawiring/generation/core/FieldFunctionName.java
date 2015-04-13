package com.metawiring.generation.core;

import com.metawiring.generation.fieldgenericfuncs.LeftString;
import com.metawiring.generation.fieldgenericfuncs.RightString;
import com.metawiring.generation.longfuncs.*;
import com.metawiring.generation.fieldgenboxes.BoxedString;
import com.metawiring.generation.fieldgenfuncs.*;
import com.metawiring.generation.fieldgenericfuncs.Prefix;
import com.metawiring.generation.fieldgenericfuncs.Suffix;

public enum FieldFunctionName {

    // population-based long->long functions
    pmodulo(PopulationModulo.class),
    pdist(PopulationSampler.class), // Population/long distribution sampling

    // generic long->long functions
    identity(LoggedIdentity.class),  // primarily for testing
    murmur3(Murmur3Hash.class),
    modulo(Modulo.class),
    multiply(Multiply.class),
    iclamp(DiscreteRangeClamp.class),
    rangedrandom(RangedRandom.class),

    // datetime long->long functions
    dateshift(DateShift.class),
    daterange(DateRange.class),
    intmod(IntModulo.class),

    // generic String->String functions

    prefix(Prefix.class),
    suffix(Suffix.class),
    index(StringIndex.class),
    leftstring(LeftString.class),
    rightstring(RightString.class),

    // long->String functions
    oneof(StringSelection.class),
    numbname(NumbNamer.class),
    numbername(NamedNumberString.class),

    // file-based long->String functions
    filesample(FileLineSampler.class),
    csvfilesample(CSVLineSampler.class),
    filecycle(FileLineCycler.class),

    // statistical sampling functions
    cdist(ContinuousDistributionSampler.class), // Continuous/Real distribution sampling
    cclamp(ContinuousRangeClamp.class),
    ddist(DiscreteDistributionSampler.class), // Discrete/Int distribution sampling


    // long->Double functions
    scaleddouble(ToScaledDouble.class),
    // Double->Double functions
    mindouble(MinDouble.class),
    maxdouble(MaxDouble.class);

    private final Class<?> implClass;

    FieldFunctionName(Class<?> implClass) {
        this.implClass = implClass;
    }

    public Class<?> getImplClass() {
        return implClass;
    }

}
