package com.metawiring.wiring;

import com.metawiring.syntax.MetagenerDSL;
import com.metawiring.syntax.ParseResult;
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
        ParseResult parseResult = MetagenerDSL.fromFile(metagenerConfig);
        if (parseResult.hasErrors()) {
            throw new RuntimeException(parseResult.getErrorSummary());
        }
        GenContext genContext = new GenContext(parseResult.getMetagenDef());
        return genContext;
    }

    public static GenContext fromString(String metagenerConfigData) {
        logger.info("loading from data, length:" + metagenerConfigData.length());
        ParseResult parseResult = MetagenerDSL.fromSyntax(metagenerConfigData);
        if (parseResult.hasErrors()) {
            throw new RuntimeException(parseResult.getErrorSummary());
        }
        GenContext genContext = new GenContext(parseResult.getMetagenDef());
        return genContext;
    }

    public static String toSyntax(MetagenDef metagenDef) {
        return MetagenerDSL.toSyntax(metagenDef);
    }
}
