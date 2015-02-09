package com.metawiring.generation.longfuncs;

import com.metawiring.generation.core.HashedSamplingAdapter;
import com.metawiring.types.functiontypes.EntityDefAware;
import com.metawiring.types.functiontypes.LongFieldFunction;
import com.metawiring.types.EntityDef;
import org.apache.commons.math3.distribution.IntegerDistribution;

/**
 * This is *NOT* threadsafe. It will be reworked to provide a more functional integration with apache commons math, if possible.
 */
public class PopulationSampler implements LongFieldFunction, EntityDefAware {

    private EntityDef entityDef;
    private IntegerDistribution dist;
    private String distributionName;
    private HashedSamplingAdapter samplingAdapter;

    public PopulationSampler(String distributionName) {
        this.distributionName = distributionName;
    }

    @Override
    public void applyEntityDef(EntityDef entityDef) {

        if (entityDef.getPopulationSize() > Integer.MAX_VALUE) {
            throw new RuntimeException("Time to upgrade the statistics library, " +
                    "it can't generate uniform samples greater than 32 bits, or use a population that is Int sized: less than 2^32-1" +
                    ", or 4295000000");
        }

        samplingAdapter = new HashedSamplingAdapter(0, (int) entityDef.getPopulationSize(), distributionName);

    }

    @Override
    public long applyAsLong(long sampleId) {
        return samplingAdapter.sample(sampleId);
    }
}
