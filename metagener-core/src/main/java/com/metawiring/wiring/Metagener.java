package com.metawiring.wiring;

import com.metawiring.syntax.MetagenerDSL;
import com.metawiring.types.MetagenDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Top level API entry point, to get users kickstarted quicly.
 */
public class Metagener {
    private static Logger logger = LoggerFactory.getLogger(Metagener.class);

    public static GenContext fromFile(String metagenerConfig) {
        logger.info("loading from " + metagenerConfig);
        MetagenDef metagenDef = MetagenerDSL.fromFile(metagenerConfig);
        GenContext genContext = new GenContext(metagenDef);
        return genContext;
    }
}
