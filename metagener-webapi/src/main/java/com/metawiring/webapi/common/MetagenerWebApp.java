package com.metawiring.webapi.common;

import com.metawiring.webapi.resource.BulkSampleResource;
import com.metawiring.webapi.resource.GenContextResource;
import com.metawiring.webapi.resource.SampleResource;
import com.metawiring.wiring.GenContext;
import com.metawiring.wiring.GenContexts;
import com.metawiring.wiring.Metagener;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MetagenerWebApp extends Application<MetagenerWebConfig> {
    private final static Logger logger = LoggerFactory.getLogger(MetagenerWebApp.class);

    private GenContexts genContexts = new GenContexts();

    public static void main(String[] args) throws Exception {
            new MetagenerWebApp().run(args);
    }

    @Override
    public void run(MetagenerWebConfig configuration, Environment environment) throws Exception {

        for (String contextName : configuration.getPreload().keySet()) {
            String contextFile = configuration.getPreload().get(contextName);
            logger.info("Preloading generator context '" + contextName + "' from '" + contextFile + "'");
            genContexts.loadDefFile(contextName, contextFile);
        }

        final GenContextResource genContextResource = new GenContextResource(genContexts);
        environment.jersey().register(genContextResource);

        final SampleResource sampleResource = new SampleResource(genContexts);
        environment.jersey().register(sampleResource);

        final BulkSampleResource bulkSampleResource = new BulkSampleResource(genContexts);
        environment.jersey().register(bulkSampleResource);

        environment.healthChecks().register("presence",new PresenceHealthCheck());
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
