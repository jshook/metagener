package com.metawiring.generation.core;

import com.metawiring.generation.fieldgenboxes.*;
import com.metawiring.generation.fieldgenericfuncs.*;
import com.metawiring.generation.longfuncs.*;
import com.metawiring.generation.fieldgenfuncs.*;
import com.metawiring.generation.stack.*;

public enum FieldFunctionName {

    // [not] autoboxing ...
    tobigdecimal(BoxedBigDecimal.class),
    tobiginteger(BoxedBigInteger.class),
    toboolean(BoxedBoolean.class),
    tobytebuffer(BoxedByteBuffer.class),
    todatetime(BoxedDateTime.class),
    todouble(BoxedDouble.class),
    tofloat(BoxedFloat.class),
    toinetaddress(BoxedInetAddress.class),
    tointeger(BoxedInteger.class),
    tolist(BoxedList.class),
    tolong(BoxedLong.class),
    tomap(BoxedMap.class),
    toset(BoxedSet.class),
    tostring(BoxedString.class),
    touuid(BoxedUUID.class),

    // stack operations
    sadd(StackAdd.class),
    ssub(StackSubtract.class),
    smul(StackMultiply.class),
    sdiv(StackDivide.class),
    spush(StackPush.class),
    spop(StackPop.class),
    sswap(StackSwap.class),
    sclear(StackClear.class),
    sexch(StackExchange.class),
    sinit(StackInit.class),

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
    intmod(IntModulo.class),

    // datetime long->long functions
    dateshift(DateShift.class),
    daterange(DateRange.class),
    datescan(DateScan.class),
    // datetime->datetime functions
    dayhours(DateTimeHours.class),

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
    fftsim(FFTSim.class),
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
