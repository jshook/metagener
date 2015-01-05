package com.metawiring.generation.entityhashfunctions;

import com.metawiring.annotations.FieldFunctionSignature;
import com.metawiring.generation.FieldFunction;
import com.metawiring.types.EntityDef;
import com.metawiring.types.EntityDefAware;
import org.apache.commons.math3.distribution.IntegerDistribution;
import org.apache.commons.math3.distribution.UniformIntegerDistribution;
import org.apache.commons.math3.random.AbstractRandomGenerator;

/**
 * This is *NOT* threadsafe. It will be reworked to provide a more functional integration with apache commons math, if possible.
 */
@FieldFunctionSignature(input=Long.class,output=Long.class)
public class PopulationSampler implements FieldFunction<Long,Long>, EntityDefAware {

    private EntityDef entityDef;
    private IntegerDistribution dist;
    private RandomGeneratorAdapter randomMapper;

    @Override
    public void applyEntityDef(EntityDef entityDef) {
        this.entityDef = entityDef;
        if (entityDef.getMaxId()>Integer.MAX_VALUE) {
             throw new RuntimeException("For now, the maximum population value must be maximum of " + Integer.MAX_VALUE);
        }
        randomMapper = new RandomGeneratorAdapter();
        Class<? extends IntegerDistribution> distClass = PopulationDistributionMapper.mapDistributionName("binomial");
        dist = PopulationDistributionMapper.mapDistribution(distClass,entityDef,randomMapper);
        dist = new UniformIntegerDistribution(randomMapper,(int) entityDef.getMinId(),(int) entityDef.getMaxId());
    }

    /**
     * NOT THREAD SAFE. This assumes that you are not using concurrent access to these functions, or at least not with
     * a higher-level synchronizer.
     * @param aLong input sample value, pseudo-random long
     * @return A population sample, idempotent with respect to the input sample value and the population
     */

    @Override
    public Long apply(Long aLong) {
        randomMapper.setSeed(aLong);
        int sample = dist.sample();
        return (long) sample;
    }

    /**
     * This is just a way to make apache commons math distributions work with
     * an externally mediated RNG source (precooked hash outputs).
     * It's ugly, and it will be rewritten. It's is _NOT_ threadsafe.
     * At least it is contained here in its wicked collusion with PopulationSample.
     * This is a loopback that is used ONLY for its side-effects, so it shouldn't
     * be used for anything except as an API work-around for PopulationSample to
     * use distributions from apache commons math.
     */
    public class RandomGeneratorAdapter extends AbstractRandomGenerator {

        private long seed;

        /**
         * This is expected to be set with a hashed value before being accessed.
         * @param seed - The hashed input
         */
        @Override
        public void setSeed(long seed) {
            this.seed= seed;
        }

        @Override
        public double nextDouble() {
            // Because of IEEE 754, you have to let the ALU do it's work to yield this value,
            // Sure, there's an off by 1 precision error here, but who's counting 1/2^64
            // The precision bias is likely to be a worse issue (1.0 and 0.0 vs 0.5)
            // There are no shortcuts for this unless you are using the Int form, which will
            // still suffer from precision bias.
            return ((double) seed ) / ((double) Long.MAX_VALUE);
        }
    }

}
