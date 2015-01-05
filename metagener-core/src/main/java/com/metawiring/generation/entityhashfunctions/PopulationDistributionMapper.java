package com.metawiring.generation.entityhashfunctions;

import com.metawiring.types.EntityDef;
import org.apache.commons.math3.distribution.*;
import org.apache.commons.math3.random.RandomGenerator;

/**
 * Map a population and a distribution together to configure a new distribution instance.
 * For each distribution, the idea is to approximate what most users might do for the population.
 * This is not meant to replace all methods of configuring distributions. It is simply the minimal
 * support for users who want a simply configuration. More advanced options may follow.
 */
public class PopulationDistributionMapper {

    public static IntegerDistribution mapDistribution(
            Class<? extends IntegerDistribution> distClass,
            EntityDef entityDef,
            RandomGenerator rng) {

        if (entityDef.getMaxId() > Integer.MAX_VALUE) {
            throw new RuntimeException("Time to upgrade the statistics library, " +
                    "it can't generate uniform samples greater than 32 bits, or use a population that is less than 2^32-1" +
                    ", or 4295000000");
        }

        switch (distClass.getSimpleName()) {

            case "UniformIntegerDistribution":
                return new UniformIntegerDistribution(rng, (int) entityDef.getMaxId(), (int) entityDef.getMaxId());
            case "BinomialDistribution":
                return new BinomialDistribution(rng, (int) entityDef.getMaxId(), 0.5d);

//            case "PascalDistribution":
//                 break;
//            case "EnumeratedIntegerDistribution":
//                break;
//            case "GeometricDistribution":
//                break;
//            case "HypergeometericDistribution":
//                break;
//            case "ZipfDistribution":
//                break;
//            case "PoissonDistribution":
//                break;
            default:
                throw new RuntimeException("distribution " + distClass.getSimpleName() + " was not a supported distribution");

        }

    }

    public static Class<? extends IntegerDistribution> mapDistributionName(String distributionName) {
        switch (distributionName) {
            case "uniform":
                return UniformIntegerDistribution.class;
            case "binomial":
                return BinomialDistribution.class;
            case "geometric":
                return GeometricDistribution.class;
            case "hypergeometric":
                return HypergeometricDistribution.class;
            case "zipf":
                return ZipfDistribution.class;
            case "poisson":
                return PoissonDistribution.class;
            case "enumerated":
                return EnumeratedIntegerDistribution.class;
            case "normal":
            case "gaussian":
                throw new RuntimeException("You probably want to use a discrete distribution for this, like binomial."
                        + " Continuous distributions are not supported yet. When they are, they will only be for field values.");
            default:
                throw new RuntimeException("Distribution name " + distributionName + " was not recognized");

        }
    }

}
