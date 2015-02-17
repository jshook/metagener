package com.metawiring.wiring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GenContexts {
    private final static Logger logger = LoggerFactory.getLogger(GenContexts.class);

    private Map<String,GenContext> contexts = new ConcurrentHashMap<>();
    private String names;

    public GenContext get(String genContextName) {
        return contexts.get(genContextName);
    }

    public void put(String genContextName, GenContext genContext) {
        contexts.put(genContextName, genContext);
    }

    public void loadDefString(String genContextName, String genContextDefData) {
        GenContext genContext = Metagener.fromString(genContextDefData);
        if (contexts.containsKey(genContextName)) {
            logger.warn("Overwriting generator context [" + genContextName + "] with a new definition");
        }
        put(genContextName, genContext);
    }

    public void loadDefFile(String genContextName, String genContextPath) {
        GenContext genContext = Metagener.fromFile(genContextPath);
        if (contexts.containsKey(genContextName)) {
            logger.warn("Overwriting generator context [" + genContextName + "] with a new definition");
        }
        put(genContextName, genContext);
    }

    public String getNames() {
        StringBuilder sb = new StringBuilder();
        for (String s : contexts.keySet()) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }
}
