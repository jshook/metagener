package com.metawiring.generation.longfuncs;

import com.metawiring.generation.core.HashedDiscreteSamplingAdapter;
import com.metawiring.types.EntityDef;
import com.metawiring.types.functiontypes.LongUnaryFieldFunction;
import org.apache.commons.math3.distribution.RealDistribution;

import java.security.InvalidParameterException;

/**
 * This is *NOT* threadsafe. It will be reworked to provide a more functional integration with apache commons math, if possible.
 */
public class DiscreteDistributionSampler implements LongUnaryFieldFunction {

    private EntityDef entityDef;
    private String[] distributionDef;
    private RealDistribution dist;
    private String distributionName;
    private HashedDiscreteSamplingAdapter samplingAdapter;

    // Odd constructors to work around commons reflection utils not recognizing varargs

    public DiscreteDistributionSampler(String distName) {
        this(new String[] { distName });
    }
    public DiscreteDistributionSampler(String distname, String param1) {
        this(new String[] { distname, param1} );
    }
    public DiscreteDistributionSampler(String distname, String param1, String param2) {
        this(new String[] { distname, param1, param2} );
    }
    public DiscreteDistributionSampler(String distname, String param1, String param2, String param3) {
        this(new String[] { distname, param1, param2, param3} );
    }

    public DiscreteDistributionSampler(String[] distributionDef) {
        if (distributionDef.length<1) {
            throw new InvalidParameterException("Double sampler requires at least a distribution name");
        }
        this.distributionDef = distributionDef;
        this.samplingAdapter = new HashedDiscreteSamplingAdapter(distributionDef);
    }

    @Override
    public long applyAsLong(long operand) {
        long sample = samplingAdapter.sample(operand);
        return sample;
    }
}
