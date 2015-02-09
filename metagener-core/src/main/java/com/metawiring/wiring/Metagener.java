package com.metawiring.wiring;

/**
 * Top level API entry point, to get users kickstarted quicly.
 */
public class Metagener {
    public static GenContext fromFile(String metagenerConfig) {
        GenContext genContext = null;
//        ConfigDefs genContextDef = MetagenerDSL.parse(memtagenerConfig);
//        GenContext genContext = new GenContext().loadDefs(genContextDef);
        return genContext;
    }

}
