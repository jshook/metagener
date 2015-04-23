package com.metawiring.descriptors;

import com.metawiring.configdefs.MutableEntityDef;
import com.metawiring.generation.longfuncs.Murmur3Hash;
import com.metawiring.generation.longfuncs.PopulationSampler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HashedSamplerTest {
    private static Logger logger = LoggerFactory.getLogger(HashedSamplerTest.class);

    @Test
    public void testUniformSamplingStride() {
        PopulationSampler ps = new PopulationSampler("uniform");
        ps.applyEntityDef(new MutableEntityDef().setName("test").setPopulationSize(100));

        List<Long> sampled = new ArrayList<>();
        for (long i=0;i<10;i++) {
            long j = i*(Long.MAX_VALUE / 10);
            sampled.add(ps.applyAsLong(j));
        }
        assertThat(sampled.get(0)).isEqualTo(0l);
        assertThat(sampled.get(1)).isEqualTo(10l);
        assertThat(sampled.get(2)).isEqualTo(20l);
        assertThat(sampled.get(3)).isEqualTo(30l);
        assertThat(sampled.get(4)).isEqualTo(40l);
        assertThat(sampled.get(5)).isEqualTo(50l);
        assertThat(sampled.get(6)).isEqualTo(60l);
        assertThat(sampled.get(7)).isEqualTo(70l);
        assertThat(sampled.get(8)).isEqualTo(80l);
        assertThat(sampled.get(9)).isEqualTo(90l);

        System.out.println(sampled);
    }

    /**
     * This is a threshold based test. It is merely to verify approximation of expected behavior.
     * A suitable replacement would be a set of diehard statistical tests, within reason. It's hashing, after all.
     */
    @Test
    public void testUniformSamplingHashed() {

        PopulationSampler ps = new PopulationSampler("uniform");
        int popSize=1000000;
        int iterations=1000000;
        long distinctThreshold=600000;

        ps.applyEntityDef(new MutableEntityDef().setName("test").setPopulationSize(popSize));

        Murmur3Hash murmur3f = new Murmur3Hash();

        List<Long> sampled = new ArrayList<>();
        for (long i=0;i<iterations;i++) {
            long k = murmur3f.applyAsLong(i);
            sampled.add(ps.applyAsLong(k));
        }

        long distinct = sampled.stream().sorted().distinct().count();

        assertThat(distinct).isGreaterThan(distinctThreshold);
        System.out.println("popsize:" + popSize + ", iterations:" + iterations + ", distinct:" + distinct);
        System.out.flush();

    }
}
