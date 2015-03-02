package com.metawiring.generation.longfuncs;

import com.metawiring.generation.core.HashedContinuousSamplingAdapter;
import com.metawiring.types.EntityDef;
import com.metawiring.types.functiontypes.TypedFieldFunction;
import org.apache.commons.math3.distribution.RealDistribution;

import java.security.InvalidParameterException;

/**
 * This is *NOT* threadsafe. It will be reworked to provide a more functional integration with apache commons math, if possible.
 */
public class ContinuousDistributionSampler implements TypedFieldFunction<Double> {

    private EntityDef entityDef;
    private String[] distributionDef;
    private RealDistribution dist;
    private String distributionName;
    private HashedContinuousSamplingAdapter samplingAdapter;

    // Odd constructors to work around commons reflection utils not recognizing varargs

    public ContinuousDistributionSampler(String distName) {
        this(new String[] { distName });
    }
    public ContinuousDistributionSampler(String distname, String param1) {
        this(new String[] { distname, param1} );
    }
    public ContinuousDistributionSampler(String distname, String param1, String param2) {
        this(new String[] { distname, param1, param2} );
    }
    public ContinuousDistributionSampler(String distname, String param1, String param2, String param3) {
        this(new String[] { distname, param1, param2, param3} );
    }

    public ContinuousDistributionSampler(String[] distributionDef) {
        if (distributionDef.length<1) {
            throw new InvalidParameterException("Double sampler requires at least a distribution name");
        }
        this.distributionDef = distributionDef;
        this.samplingAdapter = new HashedContinuousSamplingAdapter(distributionDef);
    }


    @Override
    public Double apply(long value) {
        return samplingAdapter.sample(value);
    }
}
