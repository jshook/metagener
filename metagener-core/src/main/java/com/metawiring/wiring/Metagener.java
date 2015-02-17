package com.metawiring.wiring;

import com.metawiring.syntax.MetagenerDSL;
import com.metawiring.syntax.MetagenerDSLParserResult;
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
        MetagenerDSLParserResult metagenerDSLParserResult = MetagenerDSL.fromFile(metagenerConfig);
        if (metagenerDSLParserResult.hasErrors()) {
            throw new RuntimeException(metagenerDSLParserResult.getErrorSummary());
        }
        GenContext genContext = new GenContext(metagenerDSLParserResult.getMetagenDef());
        return genContext;
    }

    public static GenContext fromString(String metagenerConfigData) {
        logger.info("loading from data, length:" + metagenerConfigData.length());
        MetagenerDSLParserResult metagenerDSLParserResult = MetagenerDSL.fromSyntax(metagenerConfigData);
        if (metagenerDSLParserResult.hasErrors()) {
            throw new RuntimeException(metagenerDSLParserResult.getErrorSummary());
        }
        GenContext genContext = new GenContext(metagenerDSLParserResult.getMetagenDef());
        return genContext;
    }

    public static String toSyntax(MetagenDef metagenDef) {
        return MetagenerDSL.toSyntax(metagenDef);
    }
}
