package com.metawiring;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.metawiring.defbuilder.DefBuilderTypes;
import com.metawiring.types.EntitySample;
import com.metawiring.types.EntitySampler;
import com.metawiring.wiring.GeneratorContext;
import org.testng.annotations.Test;

public class BasicPerfTest {
    MetricRegistry r = new MetricRegistry();

    // calculated mean rate=53784499.22/s, with 10000 loops of 100000 cycles.
    @Test(sequential = true,enabled = false)
    public void testMurmur3StringerSpeed() {

        DefBuilderTypes b = GeneratorContext.builder();
        b.entity("stringrecord").population(1000).field("astring").type("text");
        b.sampler("stringrecord").entityFunction("murmur3");
        GeneratorContext c = new GeneratorContext().loadDefs(b);
        EntitySampler s = c.getEntitySampleStream("stringrecord");

        Timer stringsTimer = r.timer("strings");

        int outerLoop=10000;
        int innerLoop=100000;

        for (int i =0; i<outerLoop; i++) {
            Timer.Context time = stringsTimer.time();

            for (int j = 0; j<innerLoop; j++) {
                EntitySample nextEntity = s.getNextEntity();

            }
            time.stop();
        }
        double v = stringsTimer.getMeanRate() * innerLoop;
        System.out.printf("calculated mean rate=%1$.2f/s, with %2$d loops of %3$d cycles.", v, outerLoop, innerLoop);
        System.out.flush();
    }

    // calculated mean rate=52930861.31/s, with 10000 loops of 100000 cycles.
    @Test(sequential = true, enabled=false)
    public void testRawStringerSpeed() {

        DefBuilderTypes b = GeneratorContext.builder();
        b.entity("stringrecord").population(1000).field("astring").type("text");
        b.sampler("stringrecord");
        GeneratorContext c = new GeneratorContext().loadDefs(b);
        EntitySampler s = c.getEntitySampleStream("stringrecord");

        Timer stringsTimer = r.timer("strings");

        int outerLoop=10000;
        int innerLoop=100000;

        for (int i =0; i<outerLoop; i++) {
            Timer.Context time = stringsTimer.time();

            for (int j = 0; j<innerLoop; j++) {
                EntitySample nextEntity = s.getNextEntity();

            }
            time.stop();
        }
        double v = stringsTimer.getMeanRate() * innerLoop;
        System.out.printf("calculated mean rate=%1$.2f/s, with %2$d loops of %3$d cycles.", v, outerLoop, innerLoop);
        System.out.flush();

    }

}
