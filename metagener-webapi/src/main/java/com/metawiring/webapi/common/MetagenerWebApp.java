package com.metawiring.webapi.common;

import com.metawiring.webapi.resource.SampleResource;
import com.metawiring.wiring.GenContext;
import com.metawiring.wiring.Metagener;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MetagenerWebApp extends Application<MetagenerWebConfig> {

    @Override
    public void run(MetagenerWebConfig configuration, Environment environment) throws Exception {
        GenContext getContext = Metagener.fromFile(configuration.getMetagenerConfig());

        final SampleResource sampleResource = new SampleResource(getContext);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void initialize(Bootstrap<MetagenerWebConfig> bootstrap) {
        super.initialize(bootstrap);
    }

}
